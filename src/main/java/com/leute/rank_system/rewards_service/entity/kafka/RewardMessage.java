package com.leute.rank_system.rewards_service.entity.kafka;

import com.leute.rank_system.rewards_service.entity.RewardTypes;

public record RewardMessage(String memberId, String guildId, RewardTypes type, String reward) {
}
