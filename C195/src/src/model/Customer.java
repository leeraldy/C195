package model;



public class Customer
{
    public int customerId;
    public String customerName;
    public String address;
    public String postalCode;
    public String phone;
    public int divisionId;
    public int countryId;
    public String divisionName;


    public Customer (int customerId, String customerName, String address, String postalCode, String phone, int divisionId, int countryId, String divisionName)
    {
        this.customerId = customerId;
        this.customerName = customerName;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.divisionId = divisionId;
        this.countryId = countryId;
        this.divisionName = divisionName;
    }


    public int getCustomerId() {
        return customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getAddress() {
        return address;
    }


    public int getDivisionId() {
        return divisionId;
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

}