package com.leute.rank_system.bot.discord.command.container.entity;

import com.leute.rank_system.bot.entity.kafka.RewardTypes;
import jakarta.annotation.Nullable;

import java.io.Serializable;

public record RewardGoal(Integer id, String guildId, String reward, RewardTypes type, Integer points, String guildName,
                         @Nullable String roleName) implements Serializable {
}
