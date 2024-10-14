package com.leute.rank_system.bot.kafka_events;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.leute.rank_system.bot.discord.Bot;
import com.leute.rank_system.bot.entity.kafka.NotificationMessage;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.awt.*;

/**
 * Consumes messages from the kafka {@code notifications} topic and handles them.
 */
@Component
public class NotificationsConsumer {

    private Bot bot;
    private Logger LOG = LoggerFactory.getLogger(NotificationsConsumer.class);

    @Autowired
    public NotificationsConsumer(Bot bot) {
        this.bot = bot;
    }

    @KafkaListener(id = "botNotificationConsumer", topics = "notifications")
    public void notificationsConsumer(@Payload String jsonMessage) throws JsonProcessingException {

        var message = JsonMapper.builder().enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS).build().readValue(jsonMessage, NotificationMessage.class);

        LOG.info("New notification: {}", message);

        var user = bot.getShard().getUserById(message.memberId());

        if (user == null) {
            LOG.warn("Member with id {} was not found", message.memberId());

            return;
        }

        user.openPrivateChannel()
                .flatMap(privateChannel -> {
                    var description = String.format("%s%n%s",
                            message.notification().title(),
                            message.notification().description());

                    var embed = new EmbedBuilder()
                            .setDescription(description)
                            .setColor(Color.BLACK)
                            .build();

                    return privateChannel.sendMessage(MessageCreateData.fromEmbeds(embed));
                }).queue();
    }

}
