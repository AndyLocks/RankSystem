package com.leute.rank_system.rewards_service.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.util.Assert;

public enum RewardTypes {

    @JsonProperty("url")
    URL("url"),
    @JsonProperty("promo_code")
    PROMO_CODE("promo_code"),
    @JsonProperty("role")
    ROLE("role");

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    RewardTypes(String name) {
        this.name = name;
    }

    public static RewardTypes getFromName(String name) {

        Assert.isNull(name, "Name cannot be nullable");

        return switch (name) {
            case "url" -> URL;
            case "promo_code" -> PROMO_CODE;
            case "role" -> ROLE;
            default -> throw new IllegalArgumentException("Cannot find reward type by name: " + name);
        };
    }
}
