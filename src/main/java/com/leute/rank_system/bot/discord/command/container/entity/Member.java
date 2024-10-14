package com.leute.rank_system.bot.discord.command.container.entity;

public record Member(String memberId, String guildId, int points, String nickname) {
}
