package com.example.booksapi.infrastructure.bookinfofetcher.http;

import lombok.Builder;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "booksapi.http.client.config")
@Builder
public record HttpClientConfigurationProperties(String uri,
                                                int limit,
                                                List<String> fields,
                                                int connectionTimeout,
                                                int readTimeout) {
}
