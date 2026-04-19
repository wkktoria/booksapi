package com.example.booksapi.infrastructure.bookinfofetcher.http;

import lombok.Builder;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "booksapi.http.client.config")
@Builder
public record HttpClientConfigurationProperties(String uri, int connectionTimeout, int readTimeout) {
}
