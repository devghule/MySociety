package com.example.mysociety;

public class User {
    private String name;
    private String contact;
    private String flatNo;
    private String wing;
    private String email;

    public User() {
        // Default constructor required for Firebase
    }

    public User(String name, String contact, String flatNo, String wing, String email) {
        this.name = name;
        this.contact = contact;
        this.flatNo = flatNo;
        this.wing = wing;
        this.email = email;
    }

    // Add public getters for all fields
    public String getName() {

        return name;
    }
    public String getContact() {

        return contact;
    }
    public String getFlatNo() {

        return flatNo;
    }
    public String getWing() {

        return wing;
    }
    public String getEmail() {

        return email;
    }
    // Add getters for other fields

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                " ,contact='" + contact + '\'' +
                " ,flatNo='" + flatNo + '\'' +
                " ,wing='" + wing + '\'' +
                " email='" + email + '\'' +
                '}';
    }
}
