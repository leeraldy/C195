package DAO;

import model.Customer;
import utilities.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;



public class DBCustomer {


    public static ObservableList<Customer> getAllCustomers()
    {
        ObservableList<Customer> customerList = FXCollections.observableArrayList();

        try
        {
            String sqlgac = "SELECT Customer_ID, Customer_Name, Address, Postal_Code, Phone, customers.Division_ID, " +
                    "first_level_divisions.COUNTRY_ID, first_level_divisions.Division FROM customers, first_level_divisions WHERE customers.Division_ID = first_level_divisions.Division_ID ORDER BY Customer_ID";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sqlgac);
            ResultSet rs = ps.executeQuery();

            while (rs.next())
            {
                int customerId = rs.getInt("Customer_ID");
                String customerName = rs.getString("Customer_Name");
                String address = rs.getString("Address");
                String postalCode = rs.getString("Postal_Code");
                String phone = rs.getString("Phone");
                int divisionId = rs.getInt("Division_ID");
                int countryId = rs.getInt("COUNTRY_ID");
                String divisionName = rs.getString("Division");

                Customer c = new Customer(customerId, customerName, address, postalCode, phone, divisionId, countryId, divisionName);
                customerList.add(c);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return customerList;
    }



    public static void addCustomer(String customerName, String address, String postalCode, String phone, int divisionId)
    {
        try
        {

            String sqlac = "INSERT INTO customers VALUES (NULL, ?, ?, ?, ?, NOW(), 'RZ', NOW(), 'RZ', ?)";

            PreparedStatement psac = DBConnection.getConnection().prepareStatement(sqlac);

            psac.setString(1, customerName);
            psac.setString(2, address);
            psac.setString(3, postalCode);
            psac.setString(4, phone  );
            psac.setInt(5, divisionId);

            psac.execute();


        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }



    }


    public static void updateCustomer(String customerName, String address, String postalCode, String phone, int divisionId, int customerId)
    {
        try
        {

            String sqluc = "UPDATE customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Division_ID = ? WHERE Customer_ID = ?";


            PreparedStatement psuc = DBConnection.getConnection().prepareStatement(sqluc);

            psuc.setString(1, customerName);
            psuc.setString(2, address);
            psuc.setString(3, postalCode);
            psuc.setString(4, phone  );
            psuc.setInt(5, divisionId);
            psuc.setInt(6, customerId);

            psuc.execute();


        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

    }

    public static void deleteCustomer(int customerId)
    {
        try {

            String sqlda = "DELETE from appointments where Customer_ID = ?";

            PreparedStatement psda = DBConnection.getConnection().prepareStatement(sqlda);

            psda.setInt(1, customerId);

            psda.execute();


            String sqldc = "DELETE from customers where Customer_ID = ?";

            PreparedStatement psdc = DBConnection.getConnection().prepareStatement(sqldc);

            psdc.setInt(1, customerId);

            psdc.execute();

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
}
