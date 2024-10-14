package com.leute.rank_system.bot.entity.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record PointsDTO(
        @JsonProperty("guild_id")
        String guildId,

        List<MemberDTO> members
) {
}
