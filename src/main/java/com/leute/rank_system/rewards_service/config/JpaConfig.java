package com.leute.rank_system.rewards_service.config;

import com.leute.rank_system.rewards_service.config.annotations.JpaRepo;
import com.leute.rank_system.rewards_service.config.annotations.RedisRepo;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(value = "com.leute.rank_system.rewards_service.repository",
        includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = JpaRepo.class),
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = RedisRepo.class))
public class JpaConfig {
}
