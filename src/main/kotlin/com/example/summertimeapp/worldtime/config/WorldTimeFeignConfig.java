package com.example.summertimeapp.worldtime.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "com.example.summertimeapp.worldtime.client")
public class WorldTimeFeignConfig {
}
