package com.example.booksapi.infrastructure.security.jwt.dto;

import lombok.Builder;

@Builder
public record TokenResponseDto(String token) {
}
