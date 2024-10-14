package com.leute.rank_system.rewards_service.entity.redis;

public record PointsCache(String memberId, String guildId, int point) {
}
