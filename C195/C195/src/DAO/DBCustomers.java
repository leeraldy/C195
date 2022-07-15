package DAO;

import Database.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customers;
import java.sql.*;


public class DBCustomers {


    public static ObservableList<Customers> getAllCustomers() throws SQLException {

        ObservableList<Customers> customerList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM customers AS c INNER JOIN first_level_divisions AS fld ON c.Division_ID = fld.Division_ID INNER JOIN countries AS co ON co.Country_ID = fld.Country_ID";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int customerID = rs.getInt("Customer_ID");
                String customerName = rs.getString("Customer_Name");
                String address = rs.getString("Address");
                String division = rs.getString("Division");
                String postalCode = rs.getString("Postal_Code");
                String country = rs.getString("Country");
                String phoneNumber = rs.getString("Phone");
                int divisionID = rs.getInt("Division_ID");

                Customers cust = new Customers(customerID, customerName, address, division, postalCode, country, phoneNumber, divisionID);
                customerList.add(cust);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return customerList;
    }


    public static void addCustomer(String customerName, String address, String postalCode, String phone, Integer divisionID) {

        try {
            String sql = "INSERT INTO customers (Customer_Name, Address, Postal_Code, Phone, Division_ID) VALUES (? , ?, ?, ?, ?)";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setString(1, customerName);
            ps.setString(2, address);
            ps.setString(3, postalCode);
            ps.setString(4, phone);
            ps.setInt(5, divisionID);

            ps.execute();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    public static void updateCustomer(String customerName, String address, String postalCode, String phone, Integer divisionID, int customerID) {

        try {
            String sql = "UPDATE customers SET Customer_name = ? , Address = ?, Postal_Code = ?, Phone = ?, Division_ID = ? WHERE Customer_ID = ?";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setString(1, customerName);
            ps.setString(2, address);
            ps.setString(3, postalCode);
            ps.setString(4, phone);
            ps.setInt(5, divisionID);
            ps.setInt(6, customerID);

            ps.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }


    public static boolean deleteCustomer(int customerID) throws SQLException {
        try {
            String sql = "DELETE from customers WHERE Customer_ID=?";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ps.setInt(1, customerID);

            ps.executeUpdate();
            if (ps.getUpdateCount() > 0) {
                System.out.println(ps.getUpdateCount() + " rows affected.");
            } else {
                System.out.println("No change occurred.");
            }
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

}