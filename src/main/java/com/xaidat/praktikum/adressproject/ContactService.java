package com.xaidat.praktikum.adressproject;

import org.springframework.ai.mcp.annotation.McpTool;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@Service
//@Component  //mit dieser Annotation sage ich dem AI Tool, dass es sich diese Klasse merken soll
public class ContactService {
    private final HashMap<Integer, Person> contactMap; //gewählte Datenstruktur für die Speicherung
    int nextId = 0; //Variable für automatische aufsteigende ID Vergabe

    public ContactService() {
        contactMap = new HashMap<Integer, Person>();
    }

    @McpTool(description = "adds a new contact to my address book")
    public void addPersonToMap(String name, String phoneNumber) {
        Person newPerson = new Person(name, phoneNumber, nextId);
        contactMap.put(nextId, newPerson);
        nextId++;
    }

    public boolean removePersonFromMap(int id) {
        Person personToBeRemoved = contactMap.get(id);
        if (personToBeRemoved != null) {
            contactMap.remove(id);
            return true;
        } else return false;
    }

    public Person updatePerson(int id, String newName, String newPhoneNumber) {
        Person personToBeUpdated = contactMap.get(id);
        if (personToBeUpdated != null) {
            personToBeUpdated.setName(newName);
            personToBeUpdated.setPhoneNumber(newPhoneNumber);
            return personToBeUpdated;
        } else return null;
    }

    @Tool(description = "returns all contacts in a list")
    public List<Person> getContactList() {
        return new ArrayList<>(contactMap.values());
    }

    //getter, eigentlich irrelevant
    public HashMap<Integer, Person> getContactMap() {
        return contactMap;
    }


}
