package com.leute.rank_system.points_service.entity;

public record KafkaPointMessage(String discordUserId, String guildId, int points) {
}
