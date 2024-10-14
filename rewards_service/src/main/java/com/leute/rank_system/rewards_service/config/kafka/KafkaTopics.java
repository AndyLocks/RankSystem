package com.leute.rank_system.rewards_service.config.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
@EnableKafka
public class KafkaTopics {

    @Bean
    public NewTopic rewardsTopic() {
        return TopicBuilder.name(Topics.REWARDS.getName())
                .partitions(5)
                .build();
    }

    @Bean
    public NewTopic notificationsTopic() {
        return TopicBuilder.name(Topics.NOTIFICATIONS.getName())
                .partitions(5)
                .build();
    }
}
