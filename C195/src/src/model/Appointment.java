package model;

import java.sql.Timestamp;


public class Appointment
{
    public int appointmentId;
    public String title;
    public String description;
    public String location;
    public int contactId;
    public String contactName;
    public String type;
    public Timestamp start;
    public Timestamp end;
    public int customerId;
    public int userId;


    public Appointment (int appointmentId, String title, String description, String location, int contactId, String contactName, String type, Timestamp start, Timestamp end, int customerId, int userId)
    {
        this.appointmentId = appointmentId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.contactId = contactId;
        this.contactName = contactName;
        this.type = type;
        this.start = start;
        this.end = end;
        this.customerId = customerId;
        this.userId = userId;
    }


    public int getAppointmentId() {
        return appointmentId;
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


    public int getContactId() {
        return contactId;
    }


    public String getContactName() {
        return contactName;
    }


    public String getType() {
        return type;
    }


    public Timestamp getStart() {
        return start;
    }


    public Timestamp getEnd() {
        return end;
    }


    public int getCustomerId() {
        return customerId;
    }


    public int getUserId() {
        return userId;
    }


    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
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


    public void setContactId(int contactId) {
        this.contactId = contactId;
    }


    public void setContactName(String contactName) {
        this.contactName = contactName;
    }


    public void setType(String type) {
        this.type = type;
    }


    public void setStart(Timestamp start) {
        this.start = start;
    }


    public void setEnd(Timestamp end) {
        this.end = end;
    }


    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }


    public void setUserId(int userId) {
        this.userId = userId;
    }

}