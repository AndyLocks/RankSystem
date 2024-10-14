package com.leute.rank_system.rewards_service.config.redis;

import com.leute.rank_system.rewards_service.config.annotations.JpaRepo;
import com.leute.rank_system.rewards_service.config.annotations.RedisRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@Configuration
@EnableRedisRepositories(value = "com.leute.rank_system.rewards_service.repository",
        includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = RedisRepo.class),
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = JpaRepo.class))
public class RedisConfig {

    @Value("${redis.host}")
    private String redisServer;

    @Value("${redis.port}")
    private int redisPort;

    private final Logger LOG = LoggerFactory.getLogger(CacheConfig.class);

    @Bean
    public RedisConnectionFactory jedisConnectionFactory() {

        LOG.debug("Redis server: {}", redisServer);
        LOG.debug("Redis port: {}", redisPort);

        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration(redisServer, redisPort);
//        configuration.setPassword(RedisPassword.of(password));
        return new JedisConnectionFactory(configuration);
    }

    @Bean
    public RedisCacheManager redisCacheManager(RedisConnectionFactory connectionFactory) {

        return RedisCacheManager.builder(connectionFactory)
                .cacheDefaults(RedisCacheConfiguration.defaultCacheConfig()
//                        .entryTtl(Duration.ofMinutes(5))
//                        .enableTimeToIdle()
                )
                .build();
    }

}
