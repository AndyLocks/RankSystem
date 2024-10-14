package com.leute.rank_system.rewards_service.entity.kafka;

public record PointsMessage(String discordUserId, String guildId, int points) {
}
