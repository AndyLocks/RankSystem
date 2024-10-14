package com.leute.rank_system.bot.service;

import com.leute.rank_system.bot.Topics;
import com.leute.rank_system.bot.discord.Bot;
import com.leute.rank_system.bot.entity.kafka.Notification;
import com.leute.rank_system.bot.entity.kafka.NotificationMessage;
import com.leute.rank_system.bot.entity.kafka.RewardMessage;
import com.leute.rank_system.bot.entity.kafka.RewardTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * Sends messages to {@link Topics#NOTIFICATIONS}
 */
@Component
public class NotificatorService {

    private Bot bot;
    private KafkaTemplate<String, NotificationMessage> notificationMessageKafkaTemplate;
    private Logger LOG = LoggerFactory.getLogger(NotificatorService.class);

    @Autowired
    public NotificatorService(Bot bot, KafkaTemplate<String, NotificationMessage> notificationMessageKafkaTemplate) {
        this.bot = bot;
        this.notificationMessageKafkaTemplate = notificationMessageKafkaTemplate;
    }

    /**
     * Sends notification of a reward.
     * <p>
     * Exaple:
     * <pre>
     * # New reward!
     * New role **role** on **Server**
     * </pre>
     *
     * @param rewardMessage message to send
     */
    public void sendRewardNotification(RewardMessage rewardMessage) {

        var guild = bot.getShard().getGuildById(rewardMessage.guildId());

        if (guild == null) {
            LOG.warn("Guild with id {} was not found", rewardMessage.guildId());
            return;
        }

        var description = switch (rewardMessage.type()) {
            case URL -> String.format("Url reward on %s:%n> %s", guild.getName(), rewardMessage.reward());
            case PROMO_CODE ->
                    String.format("New promo code reward on %s:%n> %s", guild.getName(), rewardMessage.reward());
            case ROLE -> {
                var role = guild.getRoleById(rewardMessage.reward());

                if (role == null) {
                    LOG.warn("Role with id {} was not found", rewardMessage.reward());
                    yield String.format("New role on %s", guild.getName());
                }

                yield String.format("New role **%s** on **%s**", role.getName(), guild.getName());
            }
        };

        notificationMessageKafkaTemplate.send(
                Topics.NOTIFICATIONS.getName(),
                new NotificationMessage(
                        rewardMessage.memberId(),
                        new Notification(
                                "# New reward!",
                                description
                        )
                )
        );
    }

    /**
     * Sends notification of error caused by {@link net.dv8tion.jda.api.exceptions.InsufficientPermissionException} exception.
     * <p>
     * Example:
     * <pre>
     * # Role issuance error
     * I do not have the rights to grant this role(role). Please contact the administration of this server: Server name
     * </pre>
     *
     * @param message message to send
     */
    public void rolePermissionErrorNotification(RewardMessage message) {
        if (message.type() != RewardTypes.ROLE)
            throw new IllegalArgumentException("Reward type must be only ROLE here");

        var guild = bot.getShard().getGuildById(message.guildId());

        if (guild == null) {
            LOG.warn("Guild with id {} was not found", message.guildId());
        }

        var role = guild == null ? null : guild.getRoleById(message.reward());

        if (role == null) {
            LOG.warn("Role with id {} was not found", message.reward());
        }

        var description = String.format("I do not have the rights to grant this role(%s). Please contact the administration of this server: %s",
                role == null ? message.reward() : role.getName(),
                guild == null ? message.guildId() : guild.getName());

        notificationMessageKafkaTemplate.send(
                Topics.NOTIFICATIONS.getName(),
                new NotificationMessage(
                        message.memberId(),
                        new Notification(
                                "# Role issuance error",
                                description
                        )
                )
        );

    }

}
