package DAO;


import model.Division;
import utilities.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;

public class DBDivision
{


    public static ObservableList<Division> getUSDivisions()
    {
        ObservableList<Division> USDivisions = FXCollections.observableArrayList();

        try
        {
            String sql = "SELECT * from first_level_divisions where COUNTRY_ID = 1";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next())
            {
                int divisionId = rs.getInt("Division_ID");
                String divisionName = rs.getString("Division");
                int countryId = rs.getInt("COUNTRY_ID");
                Division D = new Division(divisionId, divisionName, countryId);
                USDivisions.add(D);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return USDivisions;
    }


    public static ObservableList<Division> getUKDivisions()
    {
        ObservableList<Division> UKDivisions = FXCollections.observableArrayList();

        try
        {
            String sql = "SELECT * from first_level_divisions where COUNTRY_ID = 2";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next())
            {
                int divisionId = rs.getInt("Division_ID");
                String divisionName = rs.getString("Division");
                int countryId = rs.getInt("COUNTRY_ID");
                Division D = new Division(divisionId, divisionName, countryId);
                UKDivisions.add(D);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return UKDivisions;
    }


    public static ObservableList<Division> getCADivisions()
    {
        ObservableList<Division> CADivisions = FXCollections.observableArrayList();

        try
        {
            String sql = "SELECT * from first_level_divisions where COUNTRY_ID = 3";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next())
            {
                int divisionId = rs.getInt("Division_ID");
                String divisionName = rs.getString("Division");
                int countryId = rs.getInt("COUNTRY_ID");
                Division D = new Division(divisionId, divisionName, countryId);
                CADivisions.add(D);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return CADivisions;
    }


}
