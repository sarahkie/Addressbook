# Adressbuch-API

Ein kleines REST-API-Projekt zur Verwaltung von Kontakten (Name, Telefonnummer, ID), entstanden im Rahmen eines Praktikums. Gebaut mit **Java**, **Spring Boot** und **Maven**.

## Features

- Kontakte anlegen, abrufen, aktualisieren und löschen (CRUD)
- Suche/Filter nach Namen (Teiltreffer, Groß-/Kleinschreibung wird ignoriert)
- Automatische ID-Vergabe durch den Server
- Saubere HTTP-Status-Codes (200, 204, 404)
- Unit- und Integrationstests
- Erste Schritte Richtung MCP-Server-Anbindung (Model Context Protocol)

## Tech-Stack

- Java 25
- Spring Boot 4.1.0
- Maven
- JUnit 5 (Tests)
- Spring AI MCP Server Starter (in Arbeit)

## Voraussetzungen

- JDK 25
- Maven (oder den mitgelieferten `./mvnw` Wrapper nutzen)

## Projekt starten

```bash
./mvnw spring-boot:run
```

Die Anwendung läuft anschließend standardmäßig unter `http://localhost:8080`.

## API-Endpunkte

Basis-URL: `/app/v1/person`

| Methode | Pfad | Beschreibung |
|---|---|---|
| GET | `/app/v1/person` | Alle Kontakte abrufen |
| GET | `/app/v1/person?name={suchbegriff}` | Kontakte nach Name filtern (Teiltreffer) |
| GET | `/app/v1/person/{id}` | Einzelnen Kontakt abrufen |
| POST | `/app/v1/person` | Neuen Kontakt anlegen |
| PUT | `/app/v1/person/{id}` | Kontakt aktualisieren |
| DELETE | `/app/v1/person/{id}` | Kontakt löschen |

### Beispiel-Request (POST)

```json
POST /app/v1/person
Content-Type: application/json

{
    "name": "Anna Beispiel",
    "phoneNumber": "0123456789"
}
```

Die `id` wird automatisch vom Server vergeben und muss nicht mitgeschickt werden.

### Status-Codes

- `200 OK` — Anfrage erfolgreich
- `204 No Content` — Löschen/Aktualisieren erfolgreich, kein Inhalt
- `404 Not Found` — Kontakt mit angegebener ID existiert nicht

## Getestet mit

Manuelles Testen der Endpunkte erfolgte mit [Insomnia](https://insomnia.rest/).

## Tests ausführen

```bash
./mvnw test
```

Enthält sowohl Unit-Tests (`ContactServiceTest`, isolierte Logik ohne Server) als auch Integrationstests (End-to-End über echte HTTP-Requests, Server muss dafür laufen).

## Projektstruktur

```
src/main/java/com/xaidat/praktikum/adressproject/
├── AdressprojectApplication.java   # Einstiegspunkt
├── Person.java                     # Model
├── ContactService.java             # Logik & Datenhaltung
└── ContactController.java          # REST-Endpunkte

src/test/java/com/xaidat/praktikum/adressproject/
├── ContactServiceTest.java         # Unit-Tests
└── ...                             # Integrationstests
```

## Status / Roadmap

- [x] CRUD-Endpunkte (GET, POST, PUT, DELETE)
- [x] Such-/Filterfunktion
- [x] Saubere HTTP-Status-Codes
- [x] Unit- und Integrationstests
- [ ] MCP-Server-Anbindung (Spring AI)
- [ ] Validierung von Eingaben
- [ ] Persistente Datenhaltung (aktuell nur In-Memory via `HashMap`)

## Hinweis

Dieses Projekt dient Lernzwecken im Rahmen eines Praktikums und ist nicht für den produktiven Einsatz gedacht.
