package com.example.namaprojectfirebase;

public class User {
    public String fullName, adressText, email;

    public User(String name, String email) {

    }
    public User(String fullName, String address, String email){
        this.fullName = fullName;
        this.adressText = address;
        this.email = email;
    }
}
