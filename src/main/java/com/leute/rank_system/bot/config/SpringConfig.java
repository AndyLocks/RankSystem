package com.leute.rank_system.bot.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class SpringConfig {

    @Value("${api.host}")
    private String managerHost;

    @Bean
    public RestClient restClient() {
        return RestClient.create(managerHost);
    }

}
