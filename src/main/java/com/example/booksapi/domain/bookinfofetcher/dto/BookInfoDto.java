package com.example.booksapi.domain.bookinfofetcher.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.io.Serializable;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
public record BookInfoDto(
        @JsonProperty("author_name")
        List<String> authorName,

        @JsonProperty("first_publish_year")
        Integer firstPublishYear,

        @JsonProperty("isbn")
        List<String> isbn
) implements Serializable {
}
