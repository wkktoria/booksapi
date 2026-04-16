package com.example.booksapi.infrastructure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

@Configuration
class ClockConfig {

    @Bean
    public Clock clock() {
        return Clock.systemUTC();
    }

}
