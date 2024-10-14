package com.leute.rank_system.rewards_service.config.kafka;

public enum Topics {

    NOTIFICATIONS("notifications"),
    POINTS_FOR_REWARD("points_for_reward"),
    REWARDS("rewards");

    private final String name;

    public String getName() {
        return name;
    }

    Topics(String name) {
        this.name = name;
    }

}
