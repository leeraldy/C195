package DAO;

import Database.DBConnection;
import model.Appointments;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;
import java.time.LocalDateTime;


public class DBAppointments {


    public static ObservableList<Appointments> getAllAppointments() throws SQLException {

        ObservableList<Appointments> appointmentList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * from appointments AS a INNER JOIN contacts as cont ON a.Contact_ID = cont.Contact_ID";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int appointmentID = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                int contactID = rs.getInt("Contact_ID");
                String type = rs.getString("Type");
                LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();
                int customerID = rs.getInt("Customer_ID");
                int userID = rs.getInt("User_ID");

                Appointments appointments = new Appointments(appointmentID, title, description, location, contactID, type, start, end, customerID, userID);
                appointmentList.add(appointments);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return appointmentList;
    }


    public static ObservableList<Appointments> getAppointmentsByWeek() throws SQLException {


        ObservableList<Appointments> appointmentList = FXCollections.observableArrayList();

        String sql = "SELECT * from appointments WHERE YEARWEEK(`Start`, 1) = YEARWEEK(CURDATE(), 1);";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);


        try {
            ps.execute();
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int appointmentID = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                int contactID = rs.getInt("Contact_ID");
                String type = rs.getString("Type");
                LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();
                int customerID = rs.getInt("Customer_ID");
                int userID = rs.getInt("User_ID");

                Appointments appointmentsByWeek = new Appointments(appointmentID, title, description, location, contactID, type, start, end, customerID, userID);
                appointmentList.add(appointmentsByWeek);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return appointmentList;
    }


    public static ObservableList<Appointments> getAppointmentsByMonth() throws SQLException {

        ObservableList<Appointments> appointmentList = FXCollections.observableArrayList();


        String sql = "SELECT * from appointments WHERE Start >= LAST_DAY(CURRENT_DATE) + INTERVAL 1 DAY - INTERVAL 1 MONTH AND Start < LAST_DAY(CURRENT_DATE) + INTERVAL 1 DAY;";

        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);


        try {
            ps.execute();
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int appointmentID = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                int contactID = rs.getInt("Contact_ID");
                String type = rs.getString("Type");
                LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();
                int customerID = rs.getInt("Customer_ID");
                int userID = rs.getInt("User_ID");

                Appointments appointmentsByMonth = new Appointments(appointmentID, title, description, location, contactID, type, start, end, customerID, userID);
                appointmentList.add(appointmentsByMonth);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return appointmentList;
    }


    public static ObservableList<Appointments> getAppointmentsByCustomerID(int customerID) throws SQLException {
        ObservableList<Appointments> appointmentList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM appointments AS a INNER JOIN contacts AS c ON a.Contact_ID = c.Contact_ID WHERE Customer_ID = ?;";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setInt(1, customerID);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int appointmentID = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                int contactID = rs.getInt("Contact_ID");
                String type = rs.getString("Type");
                LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();
                rs.getInt("Customer_ID");
                int userID = rs.getInt("User_ID");

                Appointments appointments = new Appointments(appointmentID, title, description, location, contactID, type, start, end, customerID, userID);
                appointmentList.add(appointments);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return appointmentList;
    }


    public static void addAppointments(String title, String description, String location, String type, Timestamp start, Timestamp end,
                                       Integer customerID, Integer userID, Integer contactID) throws SQLIntegrityConstraintViolationException, SQLException {

        try {
            String sql = "INSERT INTO appointments (Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ps.setString(1, title);
            ps.setString(2, description);
            ps.setString(3, location);
            ps.setString(4, type);
            ps.setTimestamp(5, start);
            ps.setTimestamp(6, end);
            ps.setInt(7, customerID);
            ps.setInt(8, userID);
            ps.setInt(9, contactID);
            ps.execute();
        } catch (SQLIntegrityConstraintViolationException throwables) {
            throwables.printStackTrace();
        }

    }


    public static boolean updateAppointments(String title, String description, String location, String type, Timestamp start, Timestamp end,
                                             Integer customerID, Integer userID, Integer contactID, Integer appointmentID) throws SQLException {

        try {
            String sql = "UPDATE appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?;";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ps.setString(1, title);
            ps.setString(2, description);
            ps.setString(3, location);
            ps.setString(4, type);
            ps.setTimestamp(5, start);
            ps.setTimestamp(6, end);
            ps.setInt(7, customerID);
            ps.setInt(8, userID);
            ps.setInt(9, contactID);
            ps.setInt(10, appointmentID);
            ps.executeUpdate();

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


    public static boolean deleteAppointments(int appointmentID) throws SQLException {
        try {
            String sql = "DELETE from appointments WHERE Appointment_ID =?";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ps.setInt(1, appointmentID);

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


    public static String reportAppointmentContact() {
        try {

            StringBuilder reportAppointmentEachContact = new StringBuilder("Contact ID | Appointment ID | Customer ID | Title | Type | Description | Start | End\n");
            String sql = "SELECT Contact_ID, Appointment_ID, Customer_ID, Title, Type, Description, Start, End FROM appointments ORDER BY Contact_ID ";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int contactID = rs.getInt("Contact_ID");
                int appointmentID = rs.getInt("Appointment_ID");
                int customerID = rs.getInt("Customer_ID");
                String title = rs.getString("Title");
                String type = rs.getString("Type");
                String description = rs.getString("Description");
                LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();

                reportAppointmentEachContact.append("\n" + contactID + "\t" + appointmentID + "\t" + customerID + "\t" + title + "\t" + type + "\t" + description + "\t" + start + "\t" + end + "\n");
            }
            return reportAppointmentEachContact.toString();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return "Try again";
        }
    }


    public static String reportAppointmentTypeMonth() {
        try {
            StringBuilder reportAppointmentPerTypeMonth = new StringBuilder("Month     | Total            ");
            reportAppointmentPerTypeMonth.append("\n");
            String sql = "SELECT MONTHNAME(start) as Month, Type, COUNT(*)  as Amount FROM appointments GROUP BY MONTH(start), type";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String month = rs.getString("Month");
                String type = rs.getString("Type");
                String amount = rs.getString("Amount");

                reportAppointmentPerTypeMonth.append(month + "\t" + type + "\t" + amount + "\n");
            }
            return reportAppointmentPerTypeMonth.toString();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return "Try again";
        }
    }


    public static String reportAppointmentTypeLocation() {
        try {
            StringBuilder reportAppointmentPerTypeLocation = new StringBuilder("Location     | Total            ");
            reportAppointmentPerTypeLocation.append("\n");

            String sql = "SELECT Location, Type, COUNT(*)  as Amount FROM appointments GROUP BY Location, type";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                String amount = rs.getString("Amount");
                reportAppointmentPerTypeLocation.append(location + "\t" + amount + "\t" + type + "\n");
            }
            return reportAppointmentPerTypeLocation.toString();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return "Try again";
        }
    }
}