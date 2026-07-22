package com.xaidat.praktikum.adressproject;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import tools.jackson.databind.ObjectMapper;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLParameters;
import java.io.IOException;
import java.net.Authenticator;
import java.net.CookieHandler;
import java.net.ProxySelector;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

import tools.jackson.databind.ObjectMapper;
import tools.jackson.core.type.TypeReference;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.web.servlet.function.RequestPredicates.POST;

@SpringBootTest
class AdressprojectApplicationTests {

    @Test
    void contextLoads() {

    }

    @Test
    void getContactListFullStack() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest getRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/app/v1/person"))
                .GET()
                .build();

        HttpResponse<String> response = client.send(getRequest, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode()); //erster Test: kommt 200 zurück?
        assertTrue(response.body().startsWith("[")); //teste, ob ein JSON-Array zurückkommt
    }


    @Test
    void addContactFullStack() throws IOException, InterruptedException {
        //send httprequest POST to localhost:8080/app/v1/person (body)
        HttpClient client = HttpClient.newHttpClient(); //neuen Client anlegen
        //für einen echten POST Request brauche ich einen Body

        String jsonBody = """ 
                {
                    "name": "Test Person",
                    "phoneNumber": "123456789"
                }
                """; //""" macht einen "Textblock"

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/app/v1/person"))  //die url, die es kreieren soll
                .header("Content-Type", "application/json")  //den header, den es mitschicken soll
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody)) //sagt, "das ist ein Post request", und dann den Body hinzufügen
                .build(); //das ganze zusammenbauen

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString()); //hier wird der request dann tatsächlich geschickt
        // und die Antwort aufgefangen

        assertEquals(200, response.statusCode()); //assertion, ob der Statuscode 200 ist

        // 2.Test hier: GET: prüfen, ob der Kontakt jetzt in der Liste ist
        HttpRequest getRequest = HttpRequest.newBuilder() //requestbuilder mit uri und ohne header und ohne body, weil nicht nötig im get request
                .uri(URI.create("http://localhost:8080/app/v1/person"))
                .GET()
                .build();

        HttpResponse<String> getResponse = client.send(getRequest, HttpResponse.BodyHandlers.ofString()); //request wird gesendet
        assertEquals(200, getResponse.statusCode()); //zuerst wird Statuscode überprüft
        assertTrue(getResponse.body().contains("Test Person")); //und dann, ob die Person tatsächlich da ist.
    }

    @Test
    void getPersonById_existingId_returnsPersonAndOkStatus() throws IOException, InterruptedException {

        HttpClient client = HttpClient.newHttpClient();

        // Arrange: Kontakt per POST anlegen
        String jsonBody = """
                {
                    "name": "GetById Test Person",
                    "phoneNumber": "555555555"
                }
                """;

        HttpRequest postRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/app/v1/person"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        client.send(postRequest, HttpResponse.BodyHandlers.ofString());
//Problem, ich weiß nicht, was die echte ID von dieser Person ist, weil es vielleicht nicht die erste ist!
        //Lösung: Die echte ID herausfinden: komplette Liste holen und passenden Namen suchen
        HttpRequest listRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/app/v1/person"))
                .GET()
                .build();

        HttpResponse<String> listResponse = client.send(listRequest, HttpResponse.BodyHandlers.ofString());
//dafür brauche ich einen Mapper, weil ich hier kein Spring in der Testklasse habe!
        //Mapper ist mein Übersetzer zwischen Java-Objekt und JSON-Objekt.
        ObjectMapper mapper = new ObjectMapper();
        List<Person> allPersons = mapper.readValue(listResponse.body(), new TypeReference<List<Person>>() {
        });

        int foundId = -1;
        for (Person p : allPersons) {
            if (p.getName().equals("GetById Test Person")) {
                foundId = p.getId();
            }
        }

        // Act: GET mit der echten ID
        HttpRequest getRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/app/v1/person/" + foundId))
                .GET()
                .build();

        HttpResponse<String> getResponse = client.send(getRequest, HttpResponse.BodyHandlers.ofString());

        // Assert
        assertEquals(200, getResponse.statusCode());
        assertTrue(getResponse.body().contains("GetById Test Person"));
    }

    @Test
    void getPersonById_nonExistingId_returns404() throws IOException, InterruptedException {

        HttpClient client = HttpClient.newHttpClient();
        // Arrange: Zuerst muss ich Kontakt per POST anlegen, falls es noch keinen gibt, den ich abfragen kann
        String jsonBody = """
                {
                    "name": "GetById Test Person",
                    "phoneNumber": "555555555"
                }
                """;

        HttpRequest postRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/app/v1/person"))  //die url, die es kreieren soll
                .header("Content-Type", "application/json")  //den header, den es mitschicken soll
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody)) //sagt, "das ist ein Post request", und dann den Body hinzufügen
                .build(); //das ganze zusammenbauen

        client.send(postRequest, HttpResponse.BodyHandlers.ofString()); //der Contact bekommt die ID 0 vermute ich

        //und dann den getRequest
        HttpRequest getRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/app/v1/person/999")) //nicht existente ID in der URL
                .GET()
                .build();

        HttpResponse<String> getResponse = client.send(getRequest, HttpResponse.BodyHandlers.ofString()); //request wird gesendet
        assertEquals(404, getResponse.statusCode()); //zuerst wird Statuscode überprüft - 404 muss kommen!
        assertFalse(getResponse.body().contains("GetById Test Person"));
    }
}
