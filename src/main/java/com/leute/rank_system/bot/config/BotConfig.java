package com.leute.rank_system.bot.config;

import com.leute.rank_system.bot.discord.Bot;
import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.hooks.EventListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Configuration for discord bot
 *
 * @see Bot
 */
@Configuration
public class BotConfig {

    /**
     * Takes token from .env, collects all {@link net.dv8tion.jda.api.hooks.ListenerAdapter}
     * and gives to the bot, creates and starts the bot.
     *
     * @param listenerAdapters all discord commands and events
     * @see net.dv8tion.jda.api.hooks.ListenerAdapter
     */
    @Bean
    public Bot bot(List<EventListener> listenerAdapters) {

        var bot = new Bot(Dotenv.configure().load().get("TOKEN"), listenerAdapters);
        bot.start();

        return bot;
    }

}
