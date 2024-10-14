package com.leute.rank_system.rewards_service.config.redis;

import com.leute.rank_system.rewards_service.entity.redis.GuildRewardGoalsCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

@Configuration
public class RedisGuildRewardCacheTemplate {

//    @Bean
//    public RedisTemplate<String, GuildRewardGoalsCache> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
//
//        var redisTemplate = new RedisTemplate<String, GuildRewardGoalsCache>();
//        redisTemplate.setConnectionFactory(redisConnectionFactory);
//
//        redisTemplate.setKeySerializer(RedisSerializer.string());
//        redisTemplate.setValueSerializer(RedisSerializer.json());
//
//        return redisTemplate;
//    }

}
