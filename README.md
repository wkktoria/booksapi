# Books API

- [Wymagania](#wymagania)
    - [Podstawowe funkcjonalności](#podstawowe-funkcjonalności)
    - [Bezpieczeństwo](#bezpieczeństwo)
- [Technologie](#technologie)
- [Scenariusz](#scenariusz)

## Wymagania

### Podstawowe funkcjonalności

- [ ] Pobieranie listy książek z bazy danych.

- [ ] Dodawanie nowych książek do bazy danych.

- [ ] Edycja istniejących książek w bazie danych.

- [ ] Usuwanie książek z bazy danych.

### Bezpieczeństwo

- [X] Autoryzacja użytkowników przy użyciu tokenów JWT.

- [X] Haszowanie haseł użytkowników przed zapisaniem ich w bazie danych.

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

## Scenariusz

Szczęśliwa ścieżka (happy path):

1. W bazie danych nie ma żadnych książek.
2. Użytkownik wykonuje żądanie `GET /books` bez JWT, system zwraca status **401 Unauthorized**.
3. Użytkownik wykonuje żądanie `POST /register` z JSON `{"username: "user", "password": "pass123"}`, system rejestruje
   użytkownika i zwraca status **201 Created**.
4. Użytkownik wykonuje żądanie `POST /token` z JSON `{"username: "user", "password": "pass123"}`, system zwraca JWT
   `AAAA.BBBB.CCC` oraz status **200 OK**.
5. Użytkownik wykonuje żądanie `GET /books` z nagłówkiem `Authorization: Bearer AAAA.BBBB.CCC`, system zwraca pustą
   listę książek oraz status **200 OK**.
6. Użytkownik wykonuje żądanie `POST /books` z JSON `{"title: "Clean Code"}`, system zapisuje książkę w bazie danych (
   `id=1`) i zwraca status **201 Created**.
7. Użytkownik wykonuje żądanie `POST /books` z JSON `{"title: "Effective Java"}`, system zapisuje książkę w bazie
   danych (`id=2`) i zwraca status **201 Created**.
8. Użytkownik wykonuje żądanie `GET /books` z nagłówkiem `Authorization: Bearer AAAA.BBBB.CCC`, system zwraca listę 2
   książek (`id=1` i `id=2`) oraz status **200 OK**.
9. Użytkownik wykonuje żądanie `GET /books/1` z nagłówkiem `Authorization: Bearer AAAA.BBBB.CCC`, system zwraca dane
   książki (`id=1`) oraz status `200 OK`.
10. Użytkownik wykonuje żądanie `GET /books/999` z nagłówkiem `Authorization: Bearer AAAA.BBBB.CCC`, system zwraca
    status **404 Not Found**.
11. Użytkownik wykonuje żądanie `PUT /books/1` z JSON `{"title: "Clean Architecture"}` oraz nagłówkiem
    `Authorization: Bearer AAAA.BBBB.CCC`, system aktualizuje książkę i zwraca jej dane (`id=1`) oraz status **200 OK**.
12. Użytkownik wykonuje żądanie `DELETE /books/1` z nagłówkiem `Authorization: Bearer AAAA.BBBB.CCC`, system usuwa
    książkę z bazy danych i zwraca status **204 No Content**.
13. Użytkownik wykonuje żądanie `GET /books` z nagłówkiem `Authorization: Bearer AAAA.BBBB.CCC`, system zwraca listę 1
    książki (`id=2`) oraz status **200 OK**.