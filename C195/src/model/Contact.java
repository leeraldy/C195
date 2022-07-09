package model;

import javafx.scene.control.Alert;

public class Contact {

    private int contactID;
    private String contactName;

    public Contact(String contactName) {
        this.contactName = contactName;
    }

    private Contact(Integer appointmentID, String contactName){
        this.contactID = contactID;
        this.contactName = contactName;
    }

    public int getContactID(){
        return contactID;
    }

    public String getContactName(){
        return contactName;
    }

    public boolean validateContact(String contactName) {

        String errorAlert = " ";

        if (contactName.isEmpty() || contactName.isEmpty()) {
            errorAlert = errorAlert + "Contact name is required";
        }
        else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText("Contact name is required");
            alert.setContentText(errorAlert);
            alert.showAndWait();

            return true;
        }
        return false;
    }
}
