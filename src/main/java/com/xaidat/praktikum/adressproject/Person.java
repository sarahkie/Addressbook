package com.xaidat.praktikum.adressproject;

import com.fasterxml.jackson.annotation.JsonCreator;

public class Person {
    private String name;
    private String phoneNumber;
    private String emailAddress;
    private int id;

    public Person(String name, String phoneNumber, String emailAddress,int id) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.emailAddress=emailAddress;
        this.id = id;
    }

    @JsonCreator //hier sage ich Jackson, dass es beim Erstellen des JSON Objekts DIESEN Konstruktor nehmen soll.
    public Person() {
    }

    //durch die Setter kann es nun selbst die Felder füllen.

    public void setName(String name) { this.name = name; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public void setEmailAddress(String emailAddress) {this.emailAddress=emailAddress;}

    public int getId() { return id; }
    public String getName() { return name; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getEmailAddress(){return emailAddress;}
}