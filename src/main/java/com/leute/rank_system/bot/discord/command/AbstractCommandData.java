package com.leute.rank_system.bot.discord.command;

import net.dv8tion.jda.api.interactions.commands.build.CommandData;

/**
 * An object that can return all the command information for the discord api
 * <p>
 * Classes implementing this interface are collected
 * together and used to form commands for the {@link com.leute.rank_system.bot.discord.Bot}
 */
public interface AbstractCommandData {
    /**
     * @return all information about command
     */
    CommandData getCommandData();
}
