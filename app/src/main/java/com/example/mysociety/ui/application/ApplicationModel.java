package com.example.mysociety.ui.application;

public class ApplicationModel {

    private String name;

    private String flat_No;
    private String facility;
    private String subject;

    // Required empty constructor for Firestore
    public ApplicationModel() {
    }

    public ApplicationModel(String name, String flat_No, String facility, String subject) {
        this.name = name;
        this.flat_No = flat_No;
        this.facility = facility;
        this.subject = subject;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFlatNo() {
        return flat_No;
    }

    public void setFlatNo(String flat_No) {
        this.flat_No = flat_No;
    }

    public String getFacility() {
        return facility;
    }

    public void setFacility(String facility) {
        this.facility = facility;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
