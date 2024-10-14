package com.leute.rank_system.rewards_service.entity.redis;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.util.List;
import java.util.Objects;

@RedisHash("guildRewardGoals")
public class GuildRewardGoalsCache {

    @Id
    private String guildId;

    private List<RewardGoalCache> goals;

    public GuildRewardGoalsCache() {}

    public GuildRewardGoalsCache(String guildId, List<RewardGoalCache> goals) {
        this.guildId = guildId;
        this.goals = goals;
    }

    public String getGuildId() {
        return guildId;
    }

    public void setGuildId(String guildId) {
        this.guildId = guildId;
    }

    public List<RewardGoalCache> getGoals() {
        return goals;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GuildRewardGoalsCache that = (GuildRewardGoalsCache) o;
        return Objects.equals(guildId, that.guildId) && Objects.equals(goals, that.goals);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guildId, goals);
    }

    public void setGoals(List<RewardGoalCache> goals) {
        this.goals = goals;
    }

}
