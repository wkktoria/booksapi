# Books API

- [Opis](#opis)
- [Uruchamianie](#uruchamianie)
- [Wymagania](#wymagania)
    - [Podstawowe funkcjonalności](#podstawowe-funkcjonalności)
    - [Bezpieczeństwo](#bezpieczeństwo)
- [Technologie](#technologie)
- [Scenariusz](#scenariusz)

## Opis

API do zarządzania biblioteką, które umożliwia użytkownikom dodawanie, przeglądanie i edycję książek.
Aplikacja korzysta z REST API, aby pobierać i wysyłać dane do bazy.
W bazie danych są przechowywane jedynie identyfikatory oraz tytuły książek dodatkowe informacje o książkach są pobierane
z [Open Library’s API](https://openlibrary.org/developers/api).

Aplikacja uruchamia się na porcie `:8080` i udostępnia Swagger UI pod adresem `/swagger-ui/index.html`.

## Uruchamianie

Wymagania:

- Docker & Docker Compose (do uruchomienia bazy danych)
- Java 21+
- Maven

Uruchomienie bazy danych:

```shell
docker compose up -d
```

Uruchomienie API:

```shell
./mvnw spring-boot:run
```

## Wymagania

### Podstawowe funkcjonalności

- [X] Pobieranie listy książek z bazy danych.

- [X] Dodawanie nowych książek do bazy danych.

- [X] Edycja istniejących książek w bazie danych.

- [X] Usuwanie książek z bazy danych.

### Bezpieczeństwo

- [X] Autoryzacja użytkowników przy użyciu tokenów JWT.

- [X] Haszowanie haseł użytkowników przed zapisaniem ich w bazie danych.

- [X] Zabezpieczenie endpointów aplikacji przed atakami typu SQL Injection.

## Technologie

- Java
- Spring Boot (Spring Web, Spring Security, Spring Data JPA)
- Relacyjna baza danych (PostgreSQL) + Flyway
- RestTemplate
- MockMvc, SpringBootTest, WireMock
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
6. Użytkownik wykonuje żądanie `POST /books` z JSON `{"title: "Clean Code"}` oraz nagłówkiem
   `Authorization: Bearer AAAA.BBBB.CCC`, system zapisuje książkę w bazie danych (`id=1`) i zwraca status **201 Created
   **.
7. Użytkownik wykonuje żądanie `POST /books` z JSON `{"title: "Effective Java"}` oraz nagłówkiem
   `Authorization: Bearer AAAA.BBBB.CCC`, system zapisuje książkę w bazie danych (`id=2`) i zwraca status **201 Created
   **.
8. Użytkownik wykonuje żądanie `GET /books` z nagłówkiem `Authorization: Bearer AAAA.BBBB.CCC`, system zwraca listę 2
   książek (`id=1` i `id=2`) oraz status **200 OK**.
9. Użytkownik wykonuje żądanie `GET /books/1` z nagłówkiem `Authorization: Bearer AAAA.BBBB.CCC`, system pobiera
   informacje na temat książki (`id=1`) z zewnętrznego API zwraca dane oraz status `200 OK`.
10. Użytkownik wykonuje żądanie `GET /books/999` z nagłówkiem `Authorization: Bearer AAAA.BBBB.CCC`, system zwraca
    status **404 Not Found**.
11. Użytkownik wykonuje żądanie `PUT /books/1` z JSON `{"title: "Clean Architecture"}` oraz nagłówkiem
    `Authorization: Bearer AAAA.BBBB.CCC`, system aktualizuje książkę i zwraca jej dane (`id=1`) oraz status **200 OK**.
12. Użytkownik wykonuje żądanie `DELETE /books/1` z nagłówkiem `Authorization: Bearer AAAA.BBBB.CCC`, system usuwa
    książkę z bazy danych i zwraca status **200 OK**.
13. Użytkownik wykonuje żądanie `GET /books` z nagłówkiem `Authorization: Bearer AAAA.BBBB.CCC`, system zwraca listę 1
    książki (`id=2`) oraz status **200 OK**.