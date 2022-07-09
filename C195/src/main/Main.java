package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import utils.DBConnection;

import java.sql.SQLException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("../view/Login.fxml"));
        primaryStage.setTitle("Appointment Manager");
        primaryStage.setScene(new Scene(root, 400, 250));
        primaryStage.show();

    }

    public static void main(String[] args) throws SQLException {
        DBConnection.startConnection();
         launch(args);

         DBConnection.endConnection();

    }
}
