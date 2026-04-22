package com.example.booksapi.infrastructure.bookinfofetcher.http;

import com.example.booksapi.domain.bookinfofetcher.BookInfoFetchable;
import com.example.booksapi.domain.bookinfofetcher.dto.BookInfoDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RequiredArgsConstructor
@Log4j2
public class BookInfoClient implements BookInfoFetchable {

    private final RestTemplate restTemplate;
    private final String uri;
    private final int limit;
    private final List<String> fields;

    @Override
    public BookInfoDto fetchInfo(final String title) {
        log.info("Fetching book info");
        HttpHeaders headers = new org.springframework.http.HttpHeaders();
        final HttpEntity<HttpHeaders> requestEntity = new HttpEntity<>(headers);
        try {
            final String fieldsParam = String.join(",", fields);
            final String url = uri + "?q=" + title + "&limit=" + limit + "&fields=" + fieldsParam;
            ResponseEntity<BookInfoWrapperDto> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity,
                    BookInfoWrapperDto.class);
            final BookInfoWrapperDto body = response.getBody();
            if (body == null) {
                log.error("Response body is null");
                throw new ResponseStatusException(HttpStatus.NO_CONTENT);
            }
            if (body.docs() == null || body.docs().isEmpty()) {
                log.warn("Could not find info about book: {}", title);
                return BookInfoDto.builder().build();
            }

            log.info("Book info fetched successfully: {}", body);

            return body.docs().getFirst();
        } catch (ResourceAccessException exception) {
            log.error("Error while fetching book info: {}", exception.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
