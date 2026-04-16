package com.example.booksapi.infrastructure.userregister;

import com.example.booksapi.domain.userregister.UserRegisterFacade;
import com.example.booksapi.domain.userregister.dto.RegisterRequestDto;
import com.example.booksapi.domain.userregister.dto.RegisterResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @PostMapping
    ResponseEntity<RegisterResponseDto> register(@RequestBody @Valid final RegisterRequestDto requestDto) {
        log.info("Received HTTP request to register user: {}", requestDto);
        RegisterResponseDto responseDto = userRegisterFacade.register(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

}
