package model;

import java.security.Timestamp;

public class Appointment {

    private int apptID;
    private String title;
    private String description;
    private String location;
    private int contactID;
    private String type;
    private Timestamp startTime;
    private Timestamp endTime;
    private int customerID;
    private int userID;
    private String contactName;


    public Appointment(int apptID, String title, String description, String location, int contactID, String type, Timestamp startTime, Timestamp endTime, int customerID, int userID, String contactName) {
        this.apptID = apptID;
        this.title = title;
        this.description = description;
        this.location = location;
        this.contactID = contactID;
        this.type = type;
        this.startTime = startTime;
        this.endTime = endTime;
        this.customerID = customerID;
        this.userID = userID;
        this.contactName = contactName;
    }

    //Getters

    public int getApptID() {
        return apptID;
    }

    public String getTitle() {
        return title;
    }


    public String getDescription() {
        return description;
    }


    public String getLocation() {
        return location;
    }


    public String getType() {
        return type;
    }


    public Timestamp getStartTime() {
        return startTime;
    }


    public Timestamp getEndTime() {
        return endTime;
    }


    public int getCustomerID() {
        return customerID;
    }


    public int getUserID() {
        return userID;
    }


    public int getContactID() {
        return contactID;
    }


    public String getContactName() {
        return contactName;
    }

    //Setters

    public void setApptID(int apptID) {
        this.apptID = apptID;
    }


    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setType(String type) {
        this.type = type;
    }



    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public void setUserId(int userId) {
        this.userID = userID;
    }

    public void setContactID(int contactID) {
        this.contactID = contactID;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }
}

