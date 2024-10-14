package com.leute.rank_system.points_service;

public enum Topics {

    POINTS("points"), POINTS_FOR_REWARD("points_for_reward");

    private final String name;

    Topics(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
