package com.leute.rank_system.bot.config.redis;

import com.leute.rank_system.bot.discord.command.container.AbstractContainer;
import com.leute.rank_system.bot.discord.command.container.ContainerValueRedisSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * Redis configuration for containers
 *
 * @see AbstractContainer
 * @see ContainerValueRedisSerializer
 */
@Configuration
public class ContainerTemplateConfig {

    @Bean
    public RedisTemplate<String, AbstractContainer> abstractContainerRedisTemplate(RedisConnectionFactory redisConnectionFactory) {

        var redisTemplate = new RedisTemplate<String, AbstractContainer>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);

        redisTemplate.setKeySerializer(RedisSerializer.string());
        redisTemplate.setValueSerializer(new ContainerValueRedisSerializer());

        return redisTemplate;
    }

}
