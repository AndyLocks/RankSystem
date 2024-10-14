package com.leute.rank_system.manager.config;

import com.leute.rank_system.manager.repository.JpaRepo;
import com.leute.rank_system.manager.repository.RedisRepo;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(value = "com.leute.rank_system.manager.repository",
        includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = JpaRepo.class),
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = RedisRepo.class))
public class SpringConfig {
}
