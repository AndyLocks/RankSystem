package com.leute.rank_system.rewards_service.entity.jpa;

import com.leute.rank_system.rewards_service.entity.RewardTypes;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "rewards")
public class Reward {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "member_id", nullable = false)
    private String memberId;

    @Column(name = "guild_id", nullable = false)
    private String guildId;

    @Column(nullable = false)
    private RewardTypes type;

    @Column
    private Date date;

    public Reward() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Reward(int id, String memberId, String guildId, RewardTypes type, Date date) {
        this.id = id;
        this.memberId = memberId;
        this.guildId = guildId;
        this.type = type;
        this.date = date;
    }
}
