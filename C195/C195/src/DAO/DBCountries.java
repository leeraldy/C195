package DAO;

import Database.DBConnection;
import model.Countries;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;


public class DBCountries {


    public static ObservableList<Countries> getAllCountries() throws SQLException {

        ObservableList<Countries> countryList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * from countries";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int countryID = rs.getInt("Country_ID");
                String country = rs.getString("Country");
                Countries countries = new Countries(countryID, country);
                countryList.add(countries);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return countryList;
    }


    public static Countries getCountryByDivisionID(int divisionID) throws SQLException {

        try {
            String sql = "SELECT * from countries AS c INNER JOIN first_level_divisions AS fld ON c.Country_ID = fld.Country_ID WHERE fld.Division_ID = ?";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setInt(1, divisionID);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Countries countries = new Countries(
                        rs.getInt("Country_ID"),
                        rs.getString("Country"));

                return countries;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;

    }

}