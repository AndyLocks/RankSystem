package com.leute.rank_system.rewards_service.entity.jpa;

import com.leute.rank_system.rewards_service.entity.RewardTypes;
import jakarta.persistence.*;

@Entity
@Table(name = "reward_goals")
public class RewardGoal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "guild_id", nullable = false)
    private String guildId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private RewardTypes type;

    @Column(nullable = false)
    private String reward;

    @Column(nullable = false)
    private Integer points;

    public RewardGoal() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGuildId() {
        return guildId;
    }

    public void setGuildId(String guildId) {
        this.guildId = guildId;
    }

    public RewardTypes getType() {
        return type;
    }

    public void setType(RewardTypes type) {
        this.type = type;
    }

    public String getReward() {
        return reward;
    }

    public void setReward(String reward) {
        this.reward = reward;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public RewardGoal(Integer id, String guildId, RewardTypes type, String reward, Integer points) {
        this.id = id;
        this.guildId = guildId;
        this.type = type;
        this.reward = reward;
        this.points = points;
    }

}
