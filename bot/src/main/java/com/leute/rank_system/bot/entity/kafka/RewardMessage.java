package com.leute.rank_system.bot.entity.kafka;

public record RewardMessage(String memberId, String guildId, RewardTypes type, String reward) {
}
