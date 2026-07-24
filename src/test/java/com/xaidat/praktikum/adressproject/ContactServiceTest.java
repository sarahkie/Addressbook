package com.xaidat.praktikum.adressproject;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class ContactServiceTest {
    private ContactService contactService;

    @BeforeEach
    void setUp() {
        contactService = new ContactService();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void addPersonToMap_addsPersonWithCorrectData() {
        contactService.addPersonToMap("Anna", "+43 123456", "anna@email.at");
        Person result = contactService.getContactMap().get(0);
        assertNotNull(result);
        assertEquals("Anna", result.getName());
        assertEquals("+43 123456", result.getPhoneNumber());
    }

    @Test
//testen, ob die automatische ID funkt
    void addPersonToMap_multipleContacts_assignsIncrementingIds() {
        contactService.addPersonToMap("Fred Feuerstein", "9988766555", "Fred@email.com");
        contactService.addPersonToMap("Wilma Feuerstein", "9988766555", "Wilma@email.com");
        assertEquals(0, contactService.getContactMap().get(0).getId());
        assertEquals(1, contactService.getContactMap().get(1).getId());
    }

    //hier noch irgendein edge case bei add-Methode

    @Test
    void removePersonFromMap_removePersonWithValidId() {
        contactService.addPersonToMap("Jojo Matthews", "234676755", "jojo@email");
        boolean wasRemoved = contactService.removePersonFromMap(0);
        assertTrue(wasRemoved);
        assertNull(contactService.getContactMap().get(0));
    }

    @Test
    void removePersonFromMap_removePersonWithInvalidId() {
        contactService.addPersonToMap("Julio", "938485859569", "julio@email");
        boolean wasRemoved = contactService.removePersonFromMap(999);
        assertFalse(wasRemoved); //testet, dass nichts removed wurde, weil ID nicht existiert
        assertNotNull(contactService.getContactMap().get(0)); //testet, ob Julio eh sicher nicht verändert wurde
    }

    @Test
    void updatePerson() {
    }

    @Test
    void getContactMap() {
    }

    @Test
    void getContactList() {
    }
}