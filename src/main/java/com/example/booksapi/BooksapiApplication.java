package com.example.booksapi;

import com.example.booksapi.infrastructure.security.jwt.JwtConfigurationProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({JwtConfigurationProperties.class})
public class BooksapiApplication {

    public static void main(String[] args) {
        SpringApplication.run(BooksapiApplication.class, args);
    }

}
