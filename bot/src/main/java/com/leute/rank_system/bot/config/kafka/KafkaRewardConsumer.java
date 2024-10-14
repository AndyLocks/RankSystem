package com.leute.rank_system.bot.config.kafka;

import com.fasterxml.jackson.databind.json.JsonMapper;
import com.leute.rank_system.bot.entity.kafka.RewardMessage;
import java.util.Map;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import com.fasterxml.jackson.databind.MapperFeature;


/**
 * Configuration for rewards consumer
 */
@Configuration
public class KafkaRewardConsumer {

    @Value("${kafka.host}")
    private String kafkaHost;

    @Bean
    ConcurrentKafkaListenerContainerFactory<String, RewardMessage>
    kafkaRewardListenerContainerFactory() {

        ConcurrentKafkaListenerContainerFactory<String, RewardMessage> factory =
                new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(rewardConsumerFactory());

        return factory;
    }

    @Bean
    public ConsumerFactory<String, RewardMessage> rewardConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfigs(), new StringDeserializer(),
                new JsonDeserializer<>(RewardMessage.class, JsonMapper.builder().enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS).build(), false));
    }

    private Map<String, Object> consumerConfigs() {
        Map<String, Object> props = new java.util.HashMap<>();

        props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaHost);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(JsonDeserializer.TYPE_MAPPINGS, "rewardMessage:com.leute.rank_system.bot.entity.kafka.RewardMessage");

        return props;
    }

}
