package com.xaidat.praktikum.adressproject;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

class ContactControllerTest {

    @Test
    void updateWithWrongID() {
        ContactService contactService = Mockito.mock(ContactService.class);
        ContactController underTest = new ContactController(contactService);

        ResponseEntity<Person> response = underTest.updateContact(1, new Person("Megatron", "+11111", 0));

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

}