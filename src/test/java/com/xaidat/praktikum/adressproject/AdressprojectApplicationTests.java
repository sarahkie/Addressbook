package com.xaidat.praktikum.adressproject;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class AdressprojectApplicationTests {

    @Test
    void contextLoads() {

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
    }
}
