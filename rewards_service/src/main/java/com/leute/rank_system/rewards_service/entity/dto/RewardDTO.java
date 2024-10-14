package com.leute.rank_system.rewards_service.entity.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.leute.rank_system.rewards_service.entity.RewardTypes;

import java.util.Date;

public record RewardDTO(
        @JsonProperty("guild_id")
        String guildId,
        @JsonProperty("member_id")
        String memberId,

        RewardTypes type,
        Date date
) {
}
