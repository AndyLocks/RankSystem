package com.leute.rank_system.rewards_service.entity.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.leute.rank_system.rewards_service.entity.RewardTypes;

public record RewardGoalDTO(
        Integer id,
        @JsonProperty("guild_id")
        String guildId,

        String reward,
        RewardTypes type,
        Integer points
) {
}
