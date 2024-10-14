package com.leute.rank_system.bot.kafka_events;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.leute.rank_system.bot.discord.Bot;
import com.leute.rank_system.bot.entity.kafka.RewardMessage;
import com.leute.rank_system.bot.entity.kafka.RewardTypes;
import com.leute.rank_system.bot.service.NotificatorService;
import net.dv8tion.jda.api.exceptions.InsufficientPermissionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

/**
 * Consumes messages from the kafka {@code rewards} topic and handles them.
 */
@Component
public class RewardConsumer {

    private Bot bot;
    private Logger LOG = LoggerFactory.getLogger(RewardConsumer.class);
    private NotificatorService notificatorService;

    @Autowired
    public RewardConsumer(Bot bot, NotificatorService notificatorService) {
        this.bot = bot;
        this.notificatorService = notificatorService;
    }

    @KafkaListener(id = "botRewardsConsumer", topics = "rewards")
    public void consumeRewardMessage(@Payload String jsonMessage) throws JsonProcessingException {

        var message = JsonMapper.builder().enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS).build().readValue(jsonMessage, RewardMessage.class);

        LOG.info("New reward message: {}", message);

        notificatorService.sendRewardNotification(message);

        if (!message.type().equals(RewardTypes.ROLE)) return;

        var guild = bot.getShard().getGuildById(message.guildId());

        if (guild == null) {
            LOG.warn("Guild with id {} was not found", message.guildId());
            return;
        }

        var member = guild.getMemberById(message.memberId());

        if (member == null) {
            LOG.warn("Member with id {} was not found", message.memberId());
            return;
        }

        var role = guild.getRoleById(message.reward());

        if (role == null) {
            LOG.warn("Role with id {} was not found", message.reward());
            return;
        }

        try {
            guild.addRoleToMember(member, role).queue();
        } catch (InsufficientPermissionException e) {

            LOG.warn(e.getMessage());
            notificatorService.rolePermissionErrorNotification(message);
        }
    }

}
