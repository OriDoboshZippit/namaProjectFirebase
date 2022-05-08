package com.example.namaprojectfirebase;

public class User {
    public String fullName, licenseNum, email;
    public int permission;

//    public User(String name, String email) {
//
//    }
    public User(String fullName, String license, String email, int permission){
        this.fullName = fullName;
        this.licenseNum = license;
        this.email = email;
        this.permission = permission;
    }
}
