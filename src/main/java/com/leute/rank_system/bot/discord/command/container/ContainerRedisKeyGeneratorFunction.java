package com.leute.rank_system.bot.discord.command.container;

import java.util.function.Function;

/**
 * A function that generates a key from a discord message id for a container for redis
 *
 * @see AbstractContainer
 */
public class ContainerRedisKeyGeneratorFunction implements Function<String, String> {
    /**
     * @param messageId discord message id. Also, can be an interaction id
     * @return a generated key for redis
     */
    @Override
    public String apply(String messageId) {
        return String.format("message_container:%s", messageId);
    }
}
