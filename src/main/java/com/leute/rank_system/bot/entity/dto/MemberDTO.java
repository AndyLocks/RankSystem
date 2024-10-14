package com.leute.rank_system.bot.entity.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record MemberDTO(
        @JsonProperty("member_id")
        String memberId,

        @JsonProperty("guild_id")
        String guildId,

        int points
) {
}
