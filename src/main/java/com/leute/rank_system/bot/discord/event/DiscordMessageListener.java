package com.leute.rank_system.bot.discord.event;

import com.leute.rank_system.bot.PointsProducer;
import com.leute.rank_system.bot.entity.kafka.KafkaPointMessage;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Listens to all messages and sends to a kafka topic
 */
@Component
public class DiscordMessageListener extends ListenerAdapter {

    private PointsProducer pointsProducer;
    private Logger LOGGER = LoggerFactory.getLogger(DiscordMessageListener.class);

    @Autowired
    public DiscordMessageListener(PointsProducer pointsProducer) {
        this.pointsProducer = pointsProducer;
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (!event.isFromGuild()) return;

        var member = event.getMember();
        var guild = event.getGuild();

        if (member == null || member.getUser().isBot()) return;

        var memberId = member.getId();
        var guildId = guild.getId();

        LOGGER.info("New discord message from [{}:{}]", memberId, guildId);
        pointsProducer.sendPoints(new KafkaPointMessage(memberId, guildId, 1));
    }
}
