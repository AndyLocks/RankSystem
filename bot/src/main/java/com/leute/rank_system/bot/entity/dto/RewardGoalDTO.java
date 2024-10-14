package com.leute.rank_system.bot.entity.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.leute.rank_system.bot.entity.kafka.RewardTypes;

public record RewardGoalDTO(
        Integer id,
        @JsonProperty("guild_id")
        String guildId,

        String reward,
        RewardTypes type,
        Integer points
) {
}
