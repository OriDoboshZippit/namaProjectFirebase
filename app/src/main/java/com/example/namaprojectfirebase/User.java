package com.example.namaprojectfirebase;

public class User {
    public String fullName, licenseNum, email, phoneNum, address;
    public int permission;
    public int salary;

//    public User(String name, String email) {
//
//    }
    public User(String fullName, String license, String email, String phoneNum, String address, int salary, int permission){
        System.out.println("IM IN BUILDER");
        this.fullName = fullName;
        this.licenseNum = license;
        this.email = email;
        this.phoneNum = phoneNum;
        this.address = address;
        this.salary = salary;
        this.permission = permission;
    }
}
