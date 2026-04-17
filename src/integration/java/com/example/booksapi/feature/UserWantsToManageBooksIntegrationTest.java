package com.example.booksapi.feature;

import com.example.booksapi.BaseIntegrationTest;
import com.example.booksapi.domain.bookcrud.dto.AllBooksResponseDto;
import com.example.booksapi.domain.userregister.dto.RegisterResponseDto;
import com.example.booksapi.infrastructure.security.jwt.dto.TokenResponseDto;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserWantsToManageBooksIntegrationTest extends BaseIntegrationTest {

    @Test
    void user_wants_to_manage_books_but_should_be_authenticated() throws Exception {
        // Step 1: Database is empty.

        // Step 2:
        ResultActions failedGetBooksRequest = mockMvc.perform(get("/books")
                .contentType(MediaType.APPLICATION_JSON));
        failedGetBooksRequest.andExpect(status().isUnauthorized());

        // Step 3:
        ResultActions registerRequest = mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "username": "user",
                            "password": "pass123"
                        }
                        """.trim()));
        MvcResult registerResult = registerRequest.andExpect(status().isCreated()).andReturn();
        String registerResultJson = registerResult.getResponse().getContentAsString();
        RegisterResponseDto registerResponseDto = objectMapper.readValue(registerResultJson, RegisterResponseDto.class);
        assertAll(
                () -> assertThat(registerResponseDto.message()).contains("successfully"),
                () -> assertThat(registerResponseDto.id()).isNotNull(),
                () -> assertThat(registerResponseDto.username()).isEqualTo("user")
        );

        // Step 4:
        ResultActions tokenRequest = mockMvc.perform(post("/token")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "username": "user",
                            "password": "pass123"
                        }
                        """.trim()));
        MvcResult tokenResult = tokenRequest.andExpect(status().isOk()).andReturn();
        String tokenResultJson = tokenResult.getResponse().getContentAsString();
        TokenResponseDto tokenResponseDto = objectMapper.readValue(tokenResultJson, TokenResponseDto.class);
        String token = tokenResponseDto.token();
        assertThat(token).isNotEmpty();

        // Step 5:
        ResultActions successGetBooksRequest = mockMvc.perform(get("/books")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON));
        MvcResult successGetBooksResult = successGetBooksRequest.andExpect(status().isOk()).andReturn();
        String successGetBooksJson = successGetBooksResult.getResponse().getContentAsString();
        AllBooksResponseDto emptyAllBooksResponseDto = objectMapper.readValue(successGetBooksJson, new TypeReference<>() {
        });
        assertThat(emptyAllBooksResponseDto.books().isEmpty());

        // Step 6:

        // Step 7:

        // Step 8:

        // Step 9:

        // Step 10:

        // Step 11:

        // Step 12:

        // Step 13:
    }

}
