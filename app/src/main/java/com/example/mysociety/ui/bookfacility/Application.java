package com.example.mysociety.ui.bookfacility;

// Application.java
public class Application {

    private String name;
    private String Flat_No;
    private String facility;
    private String subject;

    // Required default constructor for Firebase
    public Application() {
    }

    // Constructor with parameters
    public Application(String name, String Flat_No, String facility ,String subject) {
        this.name = name;
        this.Flat_No = Flat_No;
        this.facility = facility;
        this.subject = subject;
    }

    // Getter methods

    public String getName() {
        return name;
    }
    public String getFlat_No() {
        return Flat_No;
    }
    public String getfacility() {
        return facility;
    }
    public String getSubject() {
        return subject;
    }

}
