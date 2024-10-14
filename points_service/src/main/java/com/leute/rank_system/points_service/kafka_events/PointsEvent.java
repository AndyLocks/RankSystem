package com.leute.rank_system.points_service.kafka_events;

import com.leute.rank_system.points_service.Topics;
import com.leute.rank_system.points_service.entity.KafkaPointMessage;
import com.leute.rank_system.points_service.entity.Points;
import com.leute.rank_system.points_service.repository.PointsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class PointsEvent {

    private PointsRepository pointsRepository;
    private Logger LOGGER = LoggerFactory.getLogger(PointsEvent.class);
    private KafkaTemplate<String, KafkaPointMessage> pointsForRewardServiceKafkaTemplate;

    @Autowired
    public PointsEvent(PointsRepository pointsRepository, KafkaTemplate<String, KafkaPointMessage> pointsForRewardServiceKafkaTemplate) {
        this.pointsRepository = pointsRepository;
        this.pointsForRewardServiceKafkaTemplate = pointsForRewardServiceKafkaTemplate;
    }

    @KafkaListener(id = "pointsConsumer", topics = "points")
    public void pointsListener(KafkaPointMessage message) {
        LOGGER.info("New points message {}", message);

        var points = pointsRepository.findPointsByMemberIdAndGuildId(message.discordUserId(), message.guildId());

        if (points == null) {
            var newRecord = pointsRepository.save(new Points(0, message.discordUserId(), message.guildId() ,message.points()));
            LOGGER.info("New record was created {}", newRecord);

        } else {

            points.setPoints(points.getPoints()+ message.points());
            var newRecord = pointsRepository.save(points);
            LOGGER.info("Record was updated {}", newRecord);
        }

        var future = pointsForRewardServiceKafkaTemplate.send(Topics.POINTS_FOR_REWARD.getName(), message);

        future.whenComplete((result, ex) -> {
            if (ex == null) {
                LOGGER.info("Sent message=[ {} ] with offset=[ {} ]", points, result.getRecordMetadata().offset());
            } else {
                LOGGER.error("Unable to send message=[ {} ] due to : {}", points, ex.getMessage());

                throw new RuntimeException(ex);
            }
        });
    }

}
