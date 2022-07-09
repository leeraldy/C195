package model;

import javafx.scene.control.Alert;

public class Customer {

    private int customerID;
    private String customerName;
    public String address;
    public String postalCode;
    public String phone;
    public String divisionName;
    public int divisionID;

    public Customer(int customerID, String customerName, String address, String postalCode, String phone, String divisionName, int divisionID) {

        this.customerID = customerID;
        this.customerName = customerName;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.divisionName = divisionName;
        this.divisionID = divisionID;
    }

    // Getters

    public int getCustomerID() {
        return customerID;
    }

    public String getCustomerName() {
        return customerName;
    }


    public String getAddress() {
        return address;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getPhone() {
        return phone;
    }

    public String getDivisionName() {
        return divisionName;
    }

    public int getDivisionID() {
        return divisionID;
    }

    // Setters

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPostalCode(String postalCode)
    {
        this.postalCode = postalCode;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }

    public void setDivisionID(int divisionID) {
        this.divisionID = divisionID;
    }

    public static boolean validateCustomer(String customerName, String address, String postalCode, String phone) {

        String errorAlert = " ";

        if(customerName.equals("")) {
            errorAlert = errorAlert + " Customer Name cannot be blank";
        }

        if(address.equals("")) {
            errorAlert = errorAlert + " Address field cannot be blank";
        }

        if(postalCode.equals("")) {
            errorAlert = errorAlert + " Postal Code field cannot be blank";
        }

        if (phone.equals("")) {
            errorAlert = errorAlert + " Phone field cannot be blank";
        }

        if (errorAlert.isEmpty()) {
            return true;
        }
        else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Customers");
            alert.setHeaderText("Please ensure all fields are filled in");
            alert.setContentText(errorAlert);
            alert.showAndWait();

            return false;
        }

    }
}
