package com.example.booksapi.infrastructure.userregister;

import com.example.booksapi.domain.userregister.UserRegisterFacade;
import com.example.booksapi.domain.userregister.dto.RegisterRequestDto;
import com.example.booksapi.domain.userregister.dto.RegisterResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/register")
@RequiredArgsConstructor
@Log4j2
class UserRegisterController {

    private final UserRegisterFacade userRegisterFacade;
    private final PasswordEncoder passwordEncoder;

    @PostMapping
    ResponseEntity<RegisterResponseDto> register(@RequestBody @Valid final RegisterRequestDto requestDto) {
        log.info("Received HTTP request to register user: {}", requestDto);
        final String username = requestDto.username();
        final String password = passwordEncoder.encode(requestDto.password());
        RegisterResponseDto responseDto = userRegisterFacade.register(RegisterRequestDto.builder()
                .username(username)
                .password(password)
                .build());
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

}
