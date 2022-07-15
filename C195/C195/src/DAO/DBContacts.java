package DAO;

import Database.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Contacts;
import java.sql.*;


public class DBContacts {


    public static ObservableList<Contacts> getAllContacts() {

        ObservableList<Contacts> contactList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * from contacts";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int contactID = rs.getInt("Contact_ID");
                String contactName = rs.getString("Contact_Name");
                String contactEmail = rs.getString("Email");
                Contacts contacts = new Contacts(contactID, contactName, contactEmail);
                contactList.add(contacts);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return contactList;
    }
}