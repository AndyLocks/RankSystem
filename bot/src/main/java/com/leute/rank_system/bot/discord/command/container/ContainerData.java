package com.leute.rank_system.bot.discord.command.container;

import net.dv8tion.jda.api.interactions.components.buttons.Button;

import java.util.List;

/**
 * An object that can return the current state of buttons and embeds to display
 */
public interface ContainerData {
    /**
     * @return all current buttons to show
     */
    List<Button> buttons();

    /**
     * @return all current embeds to show
     */
    ContainerEmbed embeds();
}
