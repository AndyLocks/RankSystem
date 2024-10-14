package com.leute.rank_system.rewards_service.entity.redis;

import com.leute.rank_system.rewards_service.entity.RewardTypes;

public record RewardGoalCache(String guildId, RewardTypes type, String reward, int points) {}
