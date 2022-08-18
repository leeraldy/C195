package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.User;
import utilities.DBConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class DBUser
{


    public static ObservableList<User> getAllUsers()
    {

        ObservableList<User> userlist = FXCollections.observableArrayList();

        try
        {
            String sql = "SELECT * from users";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next())
            {
                int userId = rs.getInt("User_ID");
                String userName = rs.getString("User_Name");
                String password = rs.getString("Password");
                User u = new User(userId, userName, password);
                userlist.add(u);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return userlist;
    }


    public static int validateUser(String username, String password)
    {
        String sql = "SELECT * FROM users WHERE user_name = '" + username + "' AND password = '" + password +"'";

        try
        {
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            {
                rs.next();
                if (rs.getString("User_Name").equals(username))
                {
                    if (rs.getString("Password").equals(password))
                    {
                        return rs.getInt("User_ID");
                    }
                }
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return -1;
    }

}
