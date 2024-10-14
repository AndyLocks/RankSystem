package com.leute.rank_system.rewards_service.entity.jpa;

import jakarta.persistence.*;

@Entity
@Table(name = "points")
public class Points {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, name = "member_id")
    private String memberId;

    @Column(name = "guild_id", nullable = false)
    private String guildId;

    @Column(nullable = false)
    private Integer points;

    public Points() {}

    public Points(int id, String memberId, String guildId, Integer points) {
        this.id = id;
        this.memberId = memberId;
        this.guildId = guildId;
        this.points = points;
    }

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

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }
}
