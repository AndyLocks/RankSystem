package com.leute.rank_system.bot.discord.command.container;

import org.jetbrains.annotations.Contract;

/**
 * All discord buttons in application
 */
public enum Buttons {
    /**
     * A backward button to scrolling {@link AbstractContainer}
     *
     * @see PageNavigable
     */
    BACKWARD_BUTTON("containerBackwardButton"),

    /**
     * A forward button to scrolling {@link AbstractContainer}
     *
     * @see PageNavigable
     */
    FORWARD_BUTTON("containerForwardButton");
    private String name;

    Buttons(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get a button from a component id
     *
     * @param name the component id of button
     * @return null if {@code name} is null
     */
    @Contract("null -> null")
    public static Buttons fromName(String name) {
        return switch (name) {
            case "containerBackwardButton" -> BACKWARD_BUTTON;
            case "containerForwardButton" -> FORWARD_BUTTON;
            case null, default -> null;
        };
    }
}
