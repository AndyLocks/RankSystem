package com.leute.rank_system.bot.discord.command.container;

import java.io.Serializable;

/**
 * Embed that contains a data to be displayed
 * <p>
 * Can be serializable
 *
 * @param description main information about message
 * @see ContainerData
 */
public record ContainerEmbed(String description) implements Serializable {
}
