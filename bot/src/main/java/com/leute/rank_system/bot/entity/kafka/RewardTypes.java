package com.leute.rank_system.bot.entity.kafka;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

public enum RewardTypes {

    @JsonProperty("url")
    URL("url"),
    @JsonProperty("promo_code")
    PROMO_CODE("promo_code"),
    @JsonProperty("role")
    ROLE("role");

    private String name;

    @JsonValue
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    RewardTypes(String name) {
        this.name = name;
    }

    @JsonCreator
    public static RewardTypes fromName(String name) {
        return switch (name) {
            case "url" -> URL;
            case "promo_code" -> PROMO_CODE;
            case "role" -> ROLE;
            case null -> throw new IllegalArgumentException("Name cannot be null");
            default -> throw new IllegalArgumentException("Cannot find reward type by name: " + name);
        };
    }
    
}
