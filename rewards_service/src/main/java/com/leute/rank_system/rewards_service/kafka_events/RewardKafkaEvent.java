package com.leute.rank_system.rewards_service.kafka_events;

import com.leute.rank_system.rewards_service.config.kafka.Topics;
import com.leute.rank_system.rewards_service.entity.jpa.RewardGoal;
import com.leute.rank_system.rewards_service.entity.jpa.Reward;
import com.leute.rank_system.rewards_service.entity.kafka.PointsMessage;
import com.leute.rank_system.rewards_service.entity.kafka.RewardMessage;
import com.leute.rank_system.rewards_service.entity.redis.GuildRewardGoalsCache;
import com.leute.rank_system.rewards_service.entity.redis.PointsCache;
import com.leute.rank_system.rewards_service.entity.redis.RewardGoalCache;
import com.leute.rank_system.rewards_service.repository.jpa.PointsRepository;
import com.leute.rank_system.rewards_service.repository.jpa.RewardGoalsRepo;
import com.leute.rank_system.rewards_service.repository.jpa.RewardsRepo;
import com.leute.rank_system.rewards_service.repository.redis.GuildRewardGoalsCacheRepository;
import com.leute.rank_system.rewards_service.repository.redis.PointsCacheRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;
import java.util.List;

@Component
public class RewardKafkaEvent {

    private RewardGoalsRepo rewardsGoalsRepo;
    private GuildRewardGoalsCacheRepository guildRewardGoalsCacheRepository;
    private PointsCacheRepository pointsCacheRepository;
    private PointsRepository pointsRepository;
    private KafkaTemplate<String, RewardMessage> rewardMessageKafkaTemplate;
    private RewardsRepo rewardsRepo;
    private Logger LOG = LoggerFactory.getLogger(RewardKafkaEvent.class);

    @Autowired
    public RewardKafkaEvent(RewardGoalsRepo rewardsGoalsRepo,
                            GuildRewardGoalsCacheRepository guildRewardGoalsCacheRepository,
                            PointsCacheRepository pointsCacheRepository,
                            PointsRepository pointsRepository,
                            KafkaTemplate<String, RewardMessage> rewardMessageKafkaTemplate,
                            RewardsRepo rewardsRepo) {

        this.rewardsGoalsRepo = rewardsGoalsRepo;
        this.guildRewardGoalsCacheRepository = guildRewardGoalsCacheRepository;
        this.pointsCacheRepository = pointsCacheRepository;
        this.pointsRepository = pointsRepository;
        this.rewardMessageKafkaTemplate = rewardMessageKafkaTemplate;
        this.rewardsRepo = rewardsRepo;
    }

    @KafkaListener(id = "rewardServicePointsConsumer", topics = "points_for_reward")
    public void rewardKafkaEvent(PointsMessage message) {

        LOG.info("New Points message: {}", message);

        int oldPoints;
        int newPoints;
        PointsCache points;

        if (pointsCacheRepository.exists(message.guildId(), message.discordUserId())) {

            points = pointsCacheRepository.get(message.guildId(), message.discordUserId());
            oldPoints = points.point();
            newPoints = oldPoints + message.points();

        } else {

            var dbPoints = pointsRepository.findPointsByMemberIdAndGuildId(message.discordUserId(), message.guildId());
            points = new PointsCache(dbPoints.getMemberId(), dbPoints.getGuildId(), dbPoints.getPoints());

            pointsCacheRepository.save(points);

            newPoints = dbPoints.getPoints();
            oldPoints = dbPoints.getPoints() - message.points();

        }

        LOG.info("Current points: {}, New Points: {}", oldPoints, newPoints);

        for (var rewardGoalCache : getAllRewardGoals(message.guildId()).stream()
                .filter(rewardGoalCache -> rewardGoalCache.points() > oldPoints &&
                        rewardGoalCache.points() <= newPoints)
                .toList()) {

            LOG.info("New goal was received: {}", rewardGoalCache);

            rewardMessageKafkaTemplate.send(
                    Topics.REWARDS.getName(),
                    new RewardMessage(
                            points.memberId(),
                            rewardGoalCache.guildId(),
                            rewardGoalCache.type(),
                            rewardGoalCache.reward()
                    )
            );

            rewardsRepo.save(new Reward(
                    0,
                    points.memberId(),
                    points.guildId(),
                    rewardGoalCache.type(),
                    Date.from(Instant.now())
            ));

        }

        pointsCacheRepository.save(new PointsCache(
                points.memberId(),
                points.guildId(),
                newPoints
        ));

    }

    private PointsCache getPointsCache(String guildId, String memberId) {
        if (pointsCacheRepository.exists(guildId, memberId)) {
            return pointsCacheRepository.get(guildId, memberId);
        } else {
            var points = pointsRepository.findPointsByMemberIdAndGuildId(memberId, guildId);
            var pointsCache = new PointsCache(points.getMemberId(), points.getGuildId(), points.getPoints());

            pointsCacheRepository.save(pointsCache);

            return pointsCache;
        }
    }

    /**
     * Get all rewards from cache if exists, otherwise get from database and save to cache.
     *
     * @param guildId discord guild id
     * @return list of rewards
     */
    private List<RewardGoalCache> getAllRewardGoals(String guildId) {

        if (guildRewardGoalsCacheRepository.existsById(guildId)) {
            var goals = guildRewardGoalsCacheRepository.findById(guildId).get().getGoals();
            return goals == null ? List.of() : goals;
        } else {
            var rewardGoals = rewardsGoalsRepo.findAll(
                            Example.of(
                                    new RewardGoal(null, guildId, null, null, null),
                                    ExampleMatcher.matching()
                                            .withIgnoreNullValues()
                            )
                    ).stream()
                    .map(goals -> new RewardGoalCache(
                            goals.getGuildId(),
                            goals.getType(),
                            goals.getReward(),
                            goals.getPoints())
                    ).toList();

            guildRewardGoalsCacheRepository.save(new GuildRewardGoalsCache(guildId, rewardGoals));

            return rewardGoals;
        }
    }

}
