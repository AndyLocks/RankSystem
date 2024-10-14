package com.leute.rank_system.bot.config.kafka;

import java.util.HashMap;
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
import com.leute.rank_system.bot.entity.kafka.NotificationMessage;


/**
 * Kafka configuration for notification consumer
 */
@Configuration
public class KafkaNotificationsConsumer {

    @Value("${kafka.host}")
    private String kafkaHost;

    @Bean
    ConcurrentKafkaListenerContainerFactory<String, NotificationMessage>
    kafkaNotificationsListenerContainerFactory(ConsumerFactory<String, NotificationMessage> consumerFactory) {

        ConcurrentKafkaListenerContainerFactory<String, NotificationMessage> factory =
                new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(consumerFactory);

        return factory;

    }

    @Bean
    public ConsumerFactory<String, NotificationMessage> notficationsconsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfigs(), new StringDeserializer(),
                new JsonDeserializer<>(NotificationMessage.class, false));
    }

    private Map<String, Object> consumerConfigs() {
        Map<String, Object> props = new HashMap<>();

        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaHost);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(JsonDeserializer.TYPE_MAPPINGS, "notificationMessage:com.leute.rank_system.bot.entity.kafka.NotificationMessage, notification:com.leute.rank_system.bot.entity.kafka.Notification");

        return props;
    }

}
