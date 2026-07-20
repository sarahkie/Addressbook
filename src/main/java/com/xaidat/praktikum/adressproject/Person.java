package com.xaidat.praktikum.adressproject;

public class Person {
    private String name;
    private String phoneNumber;
    private int id;

    public Person(String name, String phoneNumber, int id) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.id = id;
    }
//leerer Konstruktor wird für Jackson benötigt.
    public Person() {
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getPhoneNumber() { return phoneNumber; }
}
