package com.example.booksapi.infrastructure.security.jwt;

import com.example.booksapi.infrastructure.security.jwt.dto.TokenRequestDto;
import com.example.booksapi.infrastructure.security.jwt.dto.TokenResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/token")
@RequiredArgsConstructor
class TokenController {

    private final JwtGenerator jwtGenerator;

    @PostMapping
    ResponseEntity<TokenResponseDto> authenticateAndGenerateToken(@RequestBody @Valid final TokenRequestDto requestDto) {
        final TokenResponseDto tokenResponseDto = jwtGenerator.authenticateAndGenerateToken(requestDto);
        return ResponseEntity.ok(tokenResponseDto);
    }

}
