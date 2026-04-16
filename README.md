# Books API

- [Wymagania](#wymagania)
    - [Podstawowe funkcjonalności](#podstawowe-funkcjonalności)
    - [Bezpieczeństwo](#bezpieczeństwo)
- [Technologie](#technologie)

## Wymagania

### Podstawowe funkcjonalności

- [ ] Pobieranie listy książek z bazy danych.

- [ ] Dodawanie nowych książek do bazy danych.

- [ ] Edycja istniejących książek w bazie danych.

- [ ] Usuwanie książek z bazy danych.

### Bezpieczeństwo

- [ ] Autoryzacja użytkowników przy użyciu tokenów JWT.

- [ ] Haszowanie haseł użytkowników przed zapisaniem ich w bazie danych.

- [ ] Zabezpieczenie endpointów aplikacji przed atakami typu SQL Injection.

## Technologie

- Java
- Spring Boot (Spring Web, Spring Security, Spring Data JPA)
- Relacyjna baza danych (PostgreSQL) + Flyway
- RestTemplate
- MockMvc, SpringBootTest
- JUnit, Mockito, AssertJ
- Testcontainers
- Lombok
- Swagger
- Docker