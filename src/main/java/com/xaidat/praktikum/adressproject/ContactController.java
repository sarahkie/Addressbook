package com.xaidat.praktikum.adressproject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/app/v1/person")

public class ContactController {

    private final ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @GetMapping
    public List<Person> returnContactList() {
        List<Person> persons = contactService.getContactList();
        return persons;
    }

    @PostMapping
    public void createNewContact(@RequestBody Person person){
        contactService.add(person.getId(),person.getName(), person.getPhoneNumber());
    }
}
