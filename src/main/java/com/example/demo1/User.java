package com.example.demo1;

public class User {
    private String firstname;
    private String lastname;
    private String password;
    private String contactInfo;

    public User(String firstname, String lastname, String password, String contactInfo) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.password = password;
        this.contactInfo = contactInfo;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }
}
