package com.example.booksapi.feature;

import com.example.booksapi.BaseIntegrationTest;
import com.example.booksapi.domain.bookcrud.dto.AllBooksResponseDto;
import com.example.booksapi.domain.bookcrud.dto.BookDto;
import com.example.booksapi.domain.bookcrud.dto.BookWithDetailsDto;
import com.example.booksapi.domain.userregister.dto.RegisterResponseDto;
import com.example.booksapi.infrastructure.security.jwt.dto.TokenResponseDto;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserWantsToManageBooksIntegrationTest extends BaseIntegrationTest {

    @Test
    void user_wants_to_manage_books_but_should_be_authenticated() throws Exception {
        // Step 1: Database is empty.

        // Step 2: User makes GET /books request without JWT and system returns 401 Unauthorized.
        ResultActions failedGetBooksRequest = mockMvc.perform(get("/books")
                .contentType(MediaType.APPLICATION_JSON));
        failedGetBooksRequest.andExpect(status().isUnauthorized());

        // Step 3: User makes POST /register request with JSON body and system registers user and returns 201 Created.
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
        RegisterResponseDto registerResponseDto = objectMapper
                .readValue(registerResultJson, RegisterResponseDto.class);
        assertAll(
                () -> assertThat(registerResponseDto.message()).contains("successfully"),
                () -> assertThat(registerResponseDto.id()).isNotNull(),
                () -> assertThat(registerResponseDto.username()).isEqualTo("user")
        );

        // Step 4: User makes POST /token request with valid JSON body and system returns the token and 200 OK.
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

        // Step 5: User makes GET /books request with valid JWT and system returns empty list of books and 200 OK.
        ResultActions successGetBooksRequest = mockMvc.perform(get("/books")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON));
        MvcResult successGetBooksResult = successGetBooksRequest.andExpect(status().isOk()).andReturn();
        String successGetBooksJson = successGetBooksResult.getResponse().getContentAsString();
        AllBooksResponseDto emptyAllBooksResponseDto = objectMapper
                .readValue(successGetBooksJson, AllBooksResponseDto.class);
        assertThat(emptyAllBooksResponseDto.books().isEmpty());

        // Step 6: User makes POST /books request with valid JWT and JSON body and system saves the book and returns 201 Created.
        ResultActions createFirstBookRequest = mockMvc.perform(post("/books")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "title": "Clean Code"
                        }
                        """.trim()));
        MvcResult createFirstBookResult = createFirstBookRequest.andExpect(status().isCreated()).andReturn();
        String createFirstBookJson = createFirstBookResult.getResponse().getContentAsString();
        BookDto fistBookDto = objectMapper.readValue(createFirstBookJson, BookDto.class);
        assertAll(
                () -> assertThat(fistBookDto.id()).isEqualTo(1),
                () -> assertThat(fistBookDto.title()).isEqualTo("Clean Code")
        );

        // Step 7: User makes POST /books request with valid JWT and JSON body and system saves the book and returns 201 Created.
        ResultActions createSecondBookRequest = mockMvc.perform(post("/books")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "title": "Effective Java"
                        }
                        """.trim()));
        MvcResult createSecondBookResult = createSecondBookRequest.andExpect(status().isCreated()).andReturn();
        String createSecondBookJson = createSecondBookResult.getResponse().getContentAsString();
        BookDto secondBookJson = objectMapper.readValue(createSecondBookJson, BookDto.class);
        assertAll(
                () -> assertThat(secondBookJson.id()).isEqualTo(2),
                () -> assertThat(secondBookJson.title()).isEqualTo("Effective Java")
        );

        // Step 8: User makes GET /books request with valid JWT and system returns list of 2 books and 200 OK.
        ResultActions getBooksRequest = mockMvc.perform(get("/books")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON));
        MvcResult getBooksResult = getBooksRequest.andExpect(status().isOk()).andReturn();
        String getBooksJson = getBooksResult.getResponse().getContentAsString();
        AllBooksResponseDto allBooksResponseDto = objectMapper.readValue(getBooksJson, AllBooksResponseDto.class);
        Set<BookDto> twoBooks = allBooksResponseDto.books();
        assertThat(twoBooks).hasSize(2);
        BookDto expectedFirstBook = twoBooks.stream().filter(book -> book.id().equals(1L)).findFirst()
                .orElseThrow(() -> new RuntimeException("Something went wrong during the integration test"));
        BookDto expectedSecondBook = twoBooks.stream().filter(book -> book.id().equals(2L)).findFirst()
                .orElseThrow(() -> new RuntimeException("Something went wrong during the integration test"));
        assertThat(twoBooks.containsAll(Set.of(expectedFirstBook, expectedSecondBook)));

        // Step 9: User makes GET /books/1 request with valid JWT and system returns book info (id=1) and returns 200 OK.
        wireMockServer.stubFor(WireMock.get(WireMock.urlPathEqualTo("/"))
                .withQueryParam("q", WireMock.equalTo("Clean Code"))
                .withQueryParam("limit", WireMock.equalTo("1"))
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", "application/json")
                        .withBody("""
                                {
                                    "docs": [
                                        {
                                            "author_name": [
                                                "Robert C. Martin"
                                            ],
                                            "first_publish_year": 2008,
                                            "isbn": [
                                                "9639637696",
                                                "9782326002272",
                                                "8328302349"
                                            ]
                                        }
                                    ]
                                }
                                """)));

        ResultActions getExisingBookRequest = mockMvc.perform(get("/books/1")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON));
        MvcResult getExisingBookResult = getExisingBookRequest.andExpect(status().isOk()).andReturn();
        String existingBookJson = getExisingBookResult.getResponse().getContentAsString();
        BookWithDetailsDto existingBookDto = objectMapper.readValue(existingBookJson, BookWithDetailsDto.class);
        assertAll(
                () -> assertNotNull(existingBookDto),
                () -> assertNotNull(existingBookDto.book()),
                () -> assertThat(existingBookDto.book().id()).isEqualTo(1L),
                () -> assertThat(existingBookDto.details().authorName()).containsExactly("Robert C. Martin"),
                () -> assertThat(existingBookDto.details().firstPublishYear()).isEqualTo(2008),
                () -> assertThat(existingBookDto.details().isbn())
                        .containsExactlyInAnyOrder("9639637696", "9782326002272", "8328302349")
        );

        // Step 10: User makes GET /books/999 with valid JWT and system returns 404 Not Found.
        ResultActions getNonExistingBookRequest = mockMvc.perform(get("/books/999")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON));
        MvcResult getNonExistingBookResult = getNonExistingBookRequest.andExpect(status().isNotFound()).andReturn();
        String getNonExistingBookJson = getNonExistingBookResult.getResponse().getContentAsString();
        assertThat(getNonExistingBookJson).contains("Not Found");

        // Step 11: User makes PUT /books/1 with valid JWT and JSON body and system returns updated book and 200 OK.
        ResultActions updateBookRequest = mockMvc.perform(put("/books/1")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "title": "Clean Architecture"
                        }
                        """.trim()));
        MvcResult updateBookResult = updateBookRequest.andExpect(status().isOk()).andReturn();
        String updateBookJson = updateBookResult.getResponse().getContentAsString();
        BookDto updatedBookDto = objectMapper.readValue(updateBookJson, BookDto.class);
        assertAll(
                () -> assertThat(updatedBookDto.id()).isEqualTo(1),
                () -> assertThat(updatedBookDto.title()).isEqualTo("Clean Architecture")
        );

        // Step 12: User makes DELETE /books/1 with valid JWT and system deletes book and returns 200 OK.
        ResultActions deleteBookRequest = mockMvc.perform(delete("/books/1")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON));
        MvcResult deleteBookResult = deleteBookRequest.andExpect(status().isOk()).andReturn();
        String deleteBookJson = deleteBookResult.getResponse().getContentAsString();
        assertThat(deleteBookJson).contains("deleted");

        // Step 13: User makes GET /books with valid JWT and system returns list of 1 book and 200 OK.
        ResultActions getBooksAfterDeleteRequest = mockMvc.perform(get("/books")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON));
        MvcResult getBookAfterDeleteResult = getBooksAfterDeleteRequest.andExpect(status().isOk()).andReturn();
        String getBooksAfterDeleteJson = getBookAfterDeleteResult.getResponse().getContentAsString();
        AllBooksResponseDto allBooksAfterDeleteResponseDto = objectMapper
                .readValue(getBooksAfterDeleteJson, AllBooksResponseDto.class);
        Set<BookDto> booksAfterDelete = allBooksAfterDeleteResponseDto.books();
        assertThat(booksAfterDelete).hasSize(1);
        BookDto expectedBookAfterDelete = booksAfterDelete.stream()
                .filter(book -> book.id().equals(2L))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Something went wrong during the integration test"));
        assertThat(booksAfterDelete.contains(expectedBookAfterDelete));
    }

}
