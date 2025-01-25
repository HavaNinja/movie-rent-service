# ✨🎞️Movie Rent Service🎞️✨️

## Features 🌟

* ➕ Add Movie to catalogue
* 📋 List all movies
* 💰 Calculate rental price for a list of movies
* 🔄 Calculate rental return price for a list of movies

## Requirements 📦

* ☕ **Java 21 SDK**
* 🐳 **Docker**

## Tech Stack 🛠️

* ☕ **Java 21**
* 🐳 **Docker**
* 🐘 **Postgres**
* 🚀 **Spring Boot**
    * 🌐 Spring Web
    * 🗃️ Spring Data JPA
    * 🔒 Spring Security
    * 🪶 Flyway

## Testing Tools 🧪

* 📦 **JUnit**
* 🤖 **Mockito**
* 🧳 **Test Containers**

### Run Local 🖥️

```bash
$ ./mvnw -Dsha1=test spring-boot:run
```
🌐 Application Port: 8080
🛠️ Application Management Port: 8081

## Required ENV Variables 🌍

* 🏠`POSTGRESQL_HOST`
* 🔌`POSTGRESQL_PORT`
* 🎥`VIDEO_RENT_STORE_DB`
* 👤`POSTGRESQL_USER`
* 🔑`POSTGRESQL_PASSWORD`
* 📝 `LOGGING_FORMAT` JSON | PLAIN

### Run Tests

`All tests 🧪`

```bash
$ ./mvnw clean test -Dsha1=test
```

`Unit tests ⚙️`

```bash
$ ./mvnw clean test -Dgroups=unit -Dsha1=test
```

`Persistence tests 🗃️`

```bash
$ ./mvnw clean test -Dgroups=persistence -Dsha1=test
```

`Component tests 🔄`

```bash
$ ./mvnw clean test -Dgroups=component -Dsha1=test
```

``` 💡NOTE💡```
`This is MVP version, therefore almost all validations,checks, exception handling are omitted in order to save time.
Application is aimed to meet only happy path scenarios.In all unexpected cases application returns http 400 Bad Request`
