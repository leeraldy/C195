package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String protocol = "jdbc";
    private static final String vendor = ":mysql:";
    private static final String location = "//localhost/";
    private static String dbName = "client_schedule";
    private static String jdbcUrl = protocol + vendor + location + dbName + "?connectionTimeZone = SERVER"; //LOCAL
    private static final String driver = "com.mysql.cj.jdbc.Driver"; // Driver reference

    private static String userName = "sqlUser"; //Username
    private static String password = "Passw0rd!"; //Password
    private static Connection conn;


    public DBConnection() {
    }

    public static void setJdbcUrl(String jdbcUrlInsert) {
        jdbcUrl = jdbcUrlInsert;
    }

    // Database name
    public static void setDbName(String dbNameInsert) {
        dbName = dbNameInsert;
    }


    //username

    public static void setUserName(String userNameInsert) {
        userName = userNameInsert;
    }


    public static void setPassword(String passwordInsert) {
        password = passwordInsert;
    }


    public static void startConnection() {
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(jdbcUrl, userName, password);
            System.out.println("Successful connection");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }

    }


    public static Connection dbConn() {

        return conn;
    }


    public static void endConnection() throws SQLException {
        try {
            conn.close();
            System.out.println("Connection closed");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
