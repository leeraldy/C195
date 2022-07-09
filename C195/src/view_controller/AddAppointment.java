package view_controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.awt.*;
import java.awt.Button;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLOutput;
import java.util.Optional;
import java.util.ResourceBundle;

public class AddAppointment implements Initializable {

    @FXML TextField titleTextField;
    @FXML TextArea descriptionField;
    @FXML TextField locationTextField;
    @FXML ComboBox<String> contactComboBox;
    @FXML TextField typeTextField;
    @FXML ComboBox<Integer> customerComboBox;
    @FXML ComboBox<Integer> userComboComboBox;
    @FXML DatePicker datePicker;
    @FXML TextField startTimeTextField;
    @FXML TextField endTimeTextField;
    @FXML Button saveButton;
    @FXML Button backButton;
    @FXML Button clearButton;

    public void backButtonHandler(ActionEvent event) throws IOException {
        System.out.println("Back button was pressed");

        ButtonType clickOkay = new ButtonType("Okay", ButtonBar.ButtonData.OK_DONE);
        Alert alert = new Alert(Alert.AlertType.WARNING, "Unable to add appointment", clickOkay);
        alert.showAndWait();

        Parent parent = FXMLLoader.load(getClass().getResource("/View/AppointmentManager.fxml"));
        Scene scene = new Scene(parent);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();

        }

        public void clearButtonHandler() {

        System.out.println("Clear Button was pressed");


        titleTextField.setText("");
        descriptionField.setText("");
        locationTextField.setText("");
        contactComboBox.getSelectionModel().clearSelection();
        typeTextField.setText("");
        customerComboBox.getSelectionModel().clearSelection();
        userComboComboBox.getSelectionModel().clearSelection();
        datePicker.getEditor().clear();
        startTimeTextField.setText("");
        endTimeTextField.setText("");

    }




    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }
}
