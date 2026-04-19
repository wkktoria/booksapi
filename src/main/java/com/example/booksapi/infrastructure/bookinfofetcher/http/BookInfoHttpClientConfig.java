package com.example.booksapi.infrastructure.bookinfofetcher.http;

import com.example.booksapi.domain.bookinfofetcher.BookInfoFetchable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class BookInfoHttpClientConfig {

    @Bean
    public RestTemplate restTemplate(final HttpClientConfigurationProperties properties) {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(Duration.ofMillis(properties.connectionTimeout()));
        requestFactory.setReadTimeout(Duration.ofMillis(properties.connectionTimeout()));
        return new RestTemplate(requestFactory);
    }

    @Bean
    public BookInfoFetchable remoteBookInfoClient(final RestTemplate restTemplate,
                                                  final HttpClientConfigurationProperties properties) {
        return new BookInfoClient(restTemplate, properties.uri());
    }

}
