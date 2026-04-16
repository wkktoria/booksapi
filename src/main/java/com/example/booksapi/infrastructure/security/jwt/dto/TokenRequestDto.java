package com.example.booksapi.infrastructure.security.jwt.dto;

import lombok.Builder;

@Builder
public record TokenRequestDto(String username, String password) {
}
