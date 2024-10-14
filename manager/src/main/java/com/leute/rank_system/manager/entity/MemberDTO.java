package com.leute.rank_system.manager.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public record MemberDTO(
        @JsonProperty("member_id")
        String memberId,

        @JsonProperty("guild_id")
        String guildId,

        int points
) {
}
