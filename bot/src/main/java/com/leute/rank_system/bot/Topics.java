package com.leute.rank_system.bot;

/**
 * All kafka topics
 */
public enum Topics {

    NOTIFICATIONS("notifications"),
    /**
     * Points received as a reward
     */
    POINTS("points");

    private final String name;

    Topics(String name) {
        this.name = name;
    }

    /**
     * @return kafka topic name
     */
    public String getName() {
        return name;
    }
}
