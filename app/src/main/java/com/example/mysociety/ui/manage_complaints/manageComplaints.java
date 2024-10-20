package com.example.mysociety.ui.manage_complaints;

public class manageComplaints {
    private String name;
    private String flatNo;
    private String complaint;

    public manageComplaints() {
        // Default constructor required for Firestore
    }

    public manageComplaints(String name, String flatNo, String complaint) {
        this.name = name;
        this.flatNo = flatNo;
        this.complaint = complaint;
    }

    public String getName() {
        return name;
    }

    public String getFlatNo() {
        return flatNo;
    }

    public String getComplaint() {
        return complaint;
    }
}
