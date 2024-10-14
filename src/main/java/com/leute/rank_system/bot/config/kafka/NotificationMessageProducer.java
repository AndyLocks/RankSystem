package com.leute.rank_system.bot.config.kafka;

import com.leute.rank_system.bot.entity.kafka.NotificationMessage;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.springframework.context.annotation.Configuration;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

/**
 * Configuration for kafka notifications consumer
 */
@Configuration
public class NotificationMessageProducer {

    @Value("${kafka.host}")
    private String kafkaHost;

    @Bean
    public ProducerFactory<String, NotificationMessage> notificationProducerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    @Bean
    public KafkaTemplate<String, NotificationMessage> notificationKafkaTemplate() {
        return new KafkaTemplate<>(notificationProducerFactory());
    }

    private Map<String, Object> producerConfigs() {

        var props = new HashMap<String, Object>();

        props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaHost);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        return props;
    }

}
