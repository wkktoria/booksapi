package com.example.booksapi.domain.userregister.dto;

import lombok.Builder;

@Builder
public record RegisterResponseDto(String message, Long id, String username) {
}
