package com.leute.rank_system.bot;

import com.leute.rank_system.bot.entity.kafka.KafkaPointMessage;
import com.leute.rank_system.bot.exception.KafkaSendMessageException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * Produces messages to {@link Topics#POINTS} kafka topic
 */
@Component
public class PointsProducer {

    private KafkaTemplate<String, KafkaPointMessage> kafkaTemplate;

    private Logger LOGGER = LoggerFactory.getLogger(PointsProducer.class);

    @Autowired
    public PointsProducer(KafkaTemplate<String, KafkaPointMessage> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    /**
     * Sends message to {@link Topics#POINTS} kafka topic
     *
     * @param points points message
     */
    public void sendPoints(KafkaPointMessage points) {
        var future = kafkaTemplate.send(Topics.POINTS.getName(), points);

        future.whenComplete((result, ex) -> {
            if (ex == null) {
                LOGGER.info("Sent message=[ {} ] with offset=[ {} ]", points, result.getRecordMetadata().offset());
            } else {
                LOGGER.error("Unable to send message=[ {} ] due to : {}", points, ex.getMessage());

                throw new KafkaSendMessageException(ex);
            }
        });
    }
}
