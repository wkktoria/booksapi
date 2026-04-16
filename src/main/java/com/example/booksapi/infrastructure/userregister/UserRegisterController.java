package com.example.booksapi.infrastructure.userregister;

import com.example.booksapi.infrastructure.userregister.dto.RegisterRequestDto;
import com.example.booksapi.infrastructure.userregister.dto.RegisterResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/register")
@RequiredArgsConstructor
@Log4j2
class UserRegisterController {

    private final UserDetailsManager userDetailsManager;

    @PostMapping
    ResponseEntity<RegisterResponseDto> register(@RequestBody @Valid final RegisterRequestDto requestDto) {
        log.info("Received HTTP request to register user: {}", requestDto);
        UserDetails user = User.builder()
                .username(requestDto.username())
                .password(requestDto.password())
                .build();
        userDetailsManager.createUser(user);
        return ResponseEntity.ok(RegisterResponseDto.builder()
                .message("User registered successfully")
                .build());
    }

}
