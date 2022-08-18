package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;
import utilities.DBConnection;

import java.sql.*;


public class DBAppointment {


    public static ObservableList<Appointment> getAllAppointments()
    {
        ObservableList<Appointment> allAppointmentList = FXCollections.observableArrayList();

        try
        {
            String sqlgaa = "SELECT Appointment_ID, Title, Description, Location, contacts.Contact_ID, contacts.Contact_Name, Type, Start, End, customers.Customer_ID, users.User_ID " +
                    "FROM appointments, contacts, customers, users WHERE appointments.Customer_ID = customers.Customer_ID AND appointments.User_ID = users.User_ID AND appointments.Contact_ID = contacts.Contact_ID  ORDER BY Appointment_ID";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sqlgaa);
            ResultSet rs = ps.executeQuery();

            while (rs.next())
            {

                int appointmentid = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                int contactId = rs.getInt("Contact_ID");
                String contactName = rs.getString("Contact_Name");
                String type = rs.getString("Type");
                Timestamp start = rs.getTimestamp("Start");
                Timestamp end = rs.getTimestamp("End");
                int customerId = rs.getInt("Customer_ID");
                int userId = rs.getInt("User_ID");


                Appointment a = new Appointment(appointmentid, title, description, location, contactId, contactName, type, start, end, customerId, userId);
                allAppointmentList.add(a);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return allAppointmentList;
    }


    public static ObservableList<Appointment> getWeekAppointments()
    {
        ObservableList<Appointment> weekAppointmentList = FXCollections.observableArrayList();

        try
        {
            String sqlgwa = "SELECT Appointment_ID, Title, Description, Location, contacts.Contact_ID, contacts.Contact_Name, Type, Start, End, customers.Customer_ID, users.User_ID FROM appointments, contacts, customers, users WHERE appointments.Customer_ID = customers.Customer_ID AND appointments.User_ID = users.User_ID AND appointments.Contact_ID = contacts.Contact_ID AND week(Start, 0) = week(curdate(), 0) ORDER BY Appointment_ID";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sqlgwa);
            ResultSet rs = ps.executeQuery();

            while (rs.next())
            {
                int appointmentId = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                int contactId = rs.getInt("Contact_ID");
                String contactName = rs.getString("Contact_Name");
                String type = rs.getString("Type");
                Timestamp start = rs.getTimestamp("Start");
                Timestamp end = rs.getTimestamp("End");


                int customerId = rs.getInt("Customer_ID");
                int userId = rs.getInt("User_ID");

                Appointment a = new Appointment(appointmentId, title, description, location, contactId, contactName, type, start, end, customerId, userId);


                weekAppointmentList.add(a);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return weekAppointmentList;
    }


    public static ObservableList<Appointment> getMonthAppointments()
    {
        ObservableList<Appointment> monthAppointmentList = FXCollections.observableArrayList();

        try
        {
            String sqlgma = "SELECT Appointment_ID, Title, Description, Location, contacts.Contact_ID, contacts.Contact_Name, Type, Start, End, customers.Customer_ID, users.User_ID FROM appointments, contacts, customers, users WHERE appointments.Customer_ID = customers.Customer_ID AND appointments.User_ID = users.User_ID AND appointments.Contact_ID = contacts.Contact_ID AND month(Start) = month(curdate()) ORDER BY Appointment_ID";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sqlgma);
            ResultSet rs = ps.executeQuery();

            while (rs.next())
            {
                int appointmentid = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                int contactId = rs.getInt("Contact_ID");
                String contactName = rs.getString("Contact_Name");
                String type = rs.getString("Type");
                Timestamp start = rs.getTimestamp("Start");
                Timestamp end = rs.getTimestamp("End");
                int customerId = rs.getInt("Customer_ID");
                int userId = rs.getInt("User_ID");

                Appointment a = new Appointment(appointmentid, title, description, location, contactId, contactName, type, start, end, customerId, userId);
                monthAppointmentList.add(a);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return monthAppointmentList;
    }

    public static Boolean checkForOverlappingAppointment (Appointment appointment)
    {
        try
        {
            String sqlcoa = "SELECT * FROM appointments WHERE ((? <= Start AND ? > Start) OR (? >= Start AND ? < End)) AND Customer_ID = ? AND Appointment_ID <> ?";

            PreparedStatement pscoa = DBConnection.getConnection().prepareStatement(sqlcoa);

            pscoa.setTimestamp(1, appointment.getStart());
            pscoa.setTimestamp(2, appointment.getEnd());
            pscoa.setTimestamp(3, appointment.getStart());
            pscoa.setTimestamp(4, appointment.getStart());
            pscoa.setInt(5, appointment.getCustomerId());
            pscoa.setInt(6, appointment.getAppointmentId());



            ResultSet rs = pscoa.executeQuery();


            while (rs.next())
            {

                return true;
            }


        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return false;
    }


    public static void addAppointment(String title, String description, String location, String type, Timestamp start, Timestamp end, int customerId, int userId, int contactId)
    {

        try

        {
            String sqlaa = "INSERT INTO appointments VALUES (NULL, ?, ?, ?, ?, ?, ?, NOW(), 'RZ', NOW(), 'RZ', ?, ?, ?)";

            PreparedStatement psaa = DBConnection.getConnection().prepareStatement(sqlaa);

            psaa.setString(1, title);
            psaa.setString(2, description);
            psaa.setString(3, location);
            psaa.setString(4, type);
            psaa.setTimestamp(5, start);
            psaa.setTimestamp(6, end);
            psaa.setInt(7, customerId);
            psaa.setInt(8, userId);
            psaa.setInt(9, contactId);


            psaa.execute();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }



    public static void updateAppointment(String title, String description, String location, String type, Timestamp start, Timestamp end, int customerId, int userId, int contactId, int appointmentId)
    {
        try
        {
            String sqlua = "UPDATE appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?";
            PreparedStatement psua = DBConnection.getConnection().prepareStatement(sqlua);

            psua.setString(1, title);
            psua.setString(2, description);
            psua.setString(3, location);
            psua.setString(4, type);
            psua.setTimestamp(5, start);
            psua.setTimestamp(6, end);
            psua.setInt(7, customerId);
            psua.setInt(8, userId);
            psua.setInt(9, contactId);
            psua.setInt(10, appointmentId);

            psua.execute();

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

    }


    public static void deleteAppointment(int appointmentId)
    {
        try {

            String sqlda = "DELETE from appointments where Appointment_ID = ?";

            PreparedStatement psda = DBConnection.getConnection().prepareStatement(sqlda);

            psda.setInt(1, appointmentId);

            psda.execute();

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }


    public static ObservableList<String> getAllTypes()
    {
        ObservableList<String> tList = FXCollections.observableArrayList();
        try {

            String sqlgat = "SELECT DISTINCT type from appointments";

            PreparedStatement psda = DBConnection.getConnection().prepareStatement(sqlgat);


            ResultSet rs = psda.executeQuery();

            while (rs.next())
            {
                tList.add(rs.getString(1));
            }

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return tList;
    }


    public static int getMonthAndTypeCount(String month, String type)
    {
        int total = 0;

        try {

            String sqlR3 = "SELECT count(*) from appointments WHERE type = ? AND monthname(start) = ?";


            PreparedStatement psda = DBConnection.getConnection().prepareStatement(sqlR3);


            psda.setString(1, type);
            psda.setString(2, month);

            ResultSet rs = psda.executeQuery();

            if (rs.next())
            {
                return rs.getInt(1);
            }

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return total;
    }
}