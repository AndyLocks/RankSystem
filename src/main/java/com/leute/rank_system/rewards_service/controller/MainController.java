package com.leute.rank_system.rewards_service.controller;

import com.leute.rank_system.rewards_service.entity.dto.RewardDTO;
import com.leute.rank_system.rewards_service.entity.dto.RewardGoalDTO;
import com.leute.rank_system.rewards_service.entity.jpa.Reward;
import com.leute.rank_system.rewards_service.entity.jpa.RewardGoal;
import com.leute.rank_system.rewards_service.repository.jpa.RewardGoalsRepo;
import com.leute.rank_system.rewards_service.repository.jpa.RewardsRepo;
import com.leute.rank_system.rewards_service.repository.redis.GuildRewardGoalsCacheRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MainController {

    private RewardGoalsRepo rewardGoalsRepo;
    private RewardsRepo rewardsRepo;
    private GuildRewardGoalsCacheRepository guildRewardGoalsCacheRepository;
    private final Logger LOG = LoggerFactory.getLogger(MainController.class);

    @Autowired
    public MainController(RewardGoalsRepo rewardGoalsRepo, RewardsRepo rewardsRepo, GuildRewardGoalsCacheRepository guildRewardGoalsCacheRepository) {
        this.rewardGoalsRepo = rewardGoalsRepo;
        this.rewardsRepo = rewardsRepo;
        this.guildRewardGoalsCacheRepository = guildRewardGoalsCacheRepository;
    }

    @PostMapping("rewards_service/new_goal")
    public void newRewardGoal(@RequestBody RewardGoalDTO rewardGoalDTO) {

        LOG.info("New goal request: [{}]", rewardGoalDTO);

        rewardGoalsRepo.save(new RewardGoal(null, rewardGoalDTO.guildId(),
                rewardGoalDTO.type(), rewardGoalDTO.reward(), rewardGoalDTO.points()));

        guildRewardGoalsCacheRepository.deleteById(rewardGoalDTO.guildId());
    }

    @GetMapping("rewards_service/goals")
    public ResponseEntity<List<RewardGoalDTO>> allRewardGoals() {

        return ResponseEntity.ok(rewardGoalsRepo.findAll().stream()
                .map(reward -> new RewardGoalDTO(
                        reward.getId(),
                        reward.getGuildId(),
                        reward.getReward(),
                        reward.getType(),
                        reward.getPoints()))
                .toList());
    }

    @GetMapping("rewards_service/goals/{guildId}")
    public ResponseEntity<List<RewardGoalDTO>> allRewardGoals(String guildId) {
        var example = Example.of(
                new RewardGoal(0, guildId, null, null, null),
                ExampleMatcher.matching()
                        .withIgnoreNullValues()
                        .withIgnorePaths("id")
        );

        return ResponseEntity.ok(
                rewardGoalsRepo.findAll(example).stream()
                        .map(reward -> new RewardGoalDTO(
                                reward.getId(),
                                reward.getGuildId(),
                                reward.getReward(),
                                reward.getType(),
                                reward.getPoints()))
                        .toList()
        );
    }

    @GetMapping("rewards_service/rewards")
    public ResponseEntity<List<RewardDTO>> allRewards() {

        return ResponseEntity.ok(
                rewardsRepo.findAll().stream()
                        .map(reward -> new RewardDTO(
                                reward.getGuildId(),
                                reward.getMemberId(),
                                reward.getType(),
                                reward.getDate()))
                        .toList()
        );
    }

    @GetMapping("rewards_service/rewards/{guildId}")
    public ResponseEntity<List<RewardDTO>> allRewards(String guildId) {
        var example = Example.of(
                new Reward(0, null, guildId, null, null),
                ExampleMatcher.matching()
                        .withIgnoreNullValues()
                        .withIgnorePaths("id")
        );

        return ResponseEntity.ok(
                rewardsRepo.findAll(example).stream()
                        .map(reward -> new RewardDTO(
                                reward.getGuildId(),
                                reward.getMemberId(),
                                reward.getType(),
                                reward.getDate()))
                        .toList()
        );
    }

    @DeleteMapping("rewards_service/reward_goal/{id}")
    public ResponseEntity<?> deleteRewardGoalById(@PathVariable int id) {
        var rewardGoal = rewardGoalsRepo.findById(id);

        if (rewardGoal.isEmpty()) {
            var problems = ProblemDetail.forStatus(404);
            problems.setTitle("Reward goal was not found by the given id");
            return ResponseEntity.of(problems).build();
        }

        rewardGoal.ifPresent(rewardGoalsRepo::delete);

        LOG.info("Reward goal with id [{}] was deleted", id);

        return ResponseEntity.ok(null);
    }

    @GetMapping("rewards_service/member_rewards/{memberId}")
    public ResponseEntity<List<RewardDTO>> allRewardsForMember(String memberId) {
        var example = Example.of(
                new Reward(0, memberId, null, null, null),
                ExampleMatcher.matching()
                        .withIgnoreNullValues()
                        .withIgnorePaths("id")
        );

        return ResponseEntity.ok(
                rewardsRepo.findAll(example).stream()
                        .map(reward -> new RewardDTO(
                                reward.getGuildId(),
                                reward.getMemberId(),
                                reward.getType(),
                                reward.getDate()))
                        .toList()
        );
    }

}
