package com.example.booksapi.apivalidationerror;

import com.example.booksapi.BaseIntegrationTest;
import com.example.booksapi.infrastructure.apivalidation.ApiValidationErrorDto;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ApiValidationFailedIntegrationTest extends BaseIntegrationTest {

    @Test
    @WithMockUser
    void should_return_400_bad_request_and_validation_message_when_book_title_empty_in_create_book_request() throws Exception {
        ResultActions createBookRequest = mockMvc.perform(post("/books")
                .contentType(MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8")
                .content("""
                        {
                            "title": ""
                        }
                        """));

        MvcResult createBookResult = createBookRequest.andExpect(status().isBadRequest()).andReturn();
        String json = createBookResult.getResponse().getContentAsString();
        ApiValidationErrorDto apiValidationErrorDto = objectMapper.readValue(json, ApiValidationErrorDto.class);
        assertAll(
                () -> assertThat(apiValidationErrorDto.status()).isEqualTo(HttpStatus.BAD_REQUEST.value()),
                () -> assertThat(apiValidationErrorDto.error()).isEqualTo(HttpStatus.BAD_REQUEST.getReasonPhrase()),
                () -> assertThat(apiValidationErrorDto.errors())
                        .anyMatch(error -> error.contains("Field 'title': must not be blank"))
        );
    }

}
