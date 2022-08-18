package DAO;


import utilities.DBConnection;
import model.Country;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;


public class DBCountry {

    public static ObservableList<Country> getAllCountries()
    {

        ObservableList<Country> countryList = FXCollections.observableArrayList();

        try
        {
            String sql = "SELECT * from countries";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next())
            {
                int countryId = rs.getInt("Country_ID");
                String countryName = rs.getString("Country");
                Country c = new Country(countryId, countryName);
                countryList.add(c);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return countryList;
    }
}
