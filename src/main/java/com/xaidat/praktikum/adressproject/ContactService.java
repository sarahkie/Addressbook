package com.xaidat.praktikum.adressproject;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class ContactService {
    private final HashMap<Integer, Person> contactMap; //gewählte Datenstruktur für die Speicherung
    int nextId = 0; //Variable für automatische aufsteigende ID Vergabe

    public ContactService() {
        contactMap = new HashMap<Integer, Person>();
    }

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
        }else return null;
    }

    //getter, eigentlich irrelevant
    public HashMap<Integer, Person> getContactMap() {
        return contactMap;
    }

    public List<Person> getContactList() {
        return new ArrayList<>(contactMap.values());
    }
}
