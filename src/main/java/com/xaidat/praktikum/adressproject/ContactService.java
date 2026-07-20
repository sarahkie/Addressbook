package com.xaidat.praktikum.adressproject;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class ContactService {
    private final HashMap<Integer,Person> contactMap;

    public ContactService() {
        contactMap = new HashMap<Integer, Person>();
    }

    public void add(int id,String name,String phoneNumber) {
        Person newPerson=new Person(name,phoneNumber,id);
        contactMap.put(id,newPerson);
    }
//getter, eigentlich irrelevant
    public HashMap<Integer, Person> getContactMap() {
        return contactMap;
    }

    public List<Person> getContactList() {
        return new ArrayList<>(contactMap.values());
    }
}
