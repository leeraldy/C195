package DAO;

import Database.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;
import model.Countries;
import model.FirstLevelDivisions;



public class DBFLDivisions {

    public static ObservableList<FirstLevelDivisions> getAllDivisions() {

        ObservableList<FirstLevelDivisions> flDivisionList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * from first_level_divisions";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int divisionID = rs.getInt("Division_ID");
                String division = rs.getString("Division");
                int countryID = rs.getInt("Country_ID");
                FirstLevelDivisions flDivisions = new FirstLevelDivisions(divisionID, division, countryID);
                flDivisionList.add(flDivisions);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return flDivisionList;
    }


    public static ObservableList<FirstLevelDivisions> getDivisionsByCountryID(int countryID) throws SQLException {

        ObservableList<FirstLevelDivisions> flDivisionList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * from first_level_divisions WHERE Country_ID  = ?";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setInt(1, countryID);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int divisionID = rs.getInt("Division_ID");
                String division = rs.getString("Division");
                rs.getInt("Country_ID");
                FirstLevelDivisions flDivisions = new FirstLevelDivisions(divisionID, division, countryID);
                flDivisionList.add(flDivisions);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return flDivisionList;
    }

}