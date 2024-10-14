package com.leute.rank_system.bot.entity.kafka;

public record KafkaPointMessage(String discordUserId, String guildId, int points) {
}
