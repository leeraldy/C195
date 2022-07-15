package controller;

import DAO.DBAppointments;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;


public class ReportsScreenController implements Initializable {

    @FXML
    private RadioButton Report1RB;

    @FXML
    private RadioButton Report2RB;

    @FXML
    private RadioButton Report3RB;

    @FXML
    private ToggleGroup viewReportTG;

    @FXML
    private Button GenerateButton;

    @FXML
    private Button ResetButton;

    @FXML
    private Button CancelButton;

    @FXML
    private TextArea ReportTextArea;


    @FXML
    void OnActionGenerateReport(ActionEvent event) {
        if (Report1RB.isSelected()) {
            ReportTextArea.setText(DBAppointments.reportAppointmentTypeMonth());
        }
        if (Report2RB.isSelected()) {
            ReportTextArea.setText(DBAppointments.reportAppointmentContact());
        }
        if (Report3RB.isSelected()) {
            ReportTextArea.setText(DBAppointments.reportAppointmentTypeLocation());
        }
    }


    @FXML
    void onActionViewReport1(ActionEvent event) {

    }

    @FXML
    void onActionViewReport2(ActionEvent event) {

    }

    @FXML
    void onActionViewReport3(ActionEvent event) {

    }


    @FXML
    void OnActionResetTextField(ActionEvent event) {
        ReportTextArea.clear();
    }


    @FXML
    void onActionCancel(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Parent scene = FXMLLoader.load(getClass().getResource("/View/MainScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.setTitle("Main");
        stage.show();
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}