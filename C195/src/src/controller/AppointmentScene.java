package controller;

import DAO.DBAppointment;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;


import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.ResourceBundle;



public class AppointmentScene implements Initializable
{
    Stage stage;
    Parent scene;


    @FXML
    private TableView<Appointment> appointmentTable;
    @FXML
    private TableColumn<Appointment, Integer> appointmentIdColumn;
    @FXML
    private TableColumn<Appointment, String> titleColumn;
    @FXML
    private TableColumn<Appointment, String> descriptionColumn;
    @FXML
    private TableColumn<Appointment, String> locationColumn;

    @FXML
    private TableColumn<Appointment, String> contactColumn;

    @FXML
    private TableColumn<Appointment, String> typeColumn;
    @FXML
    private TableColumn<Appointment, LocalDateTime> startColumn;
    @FXML
    private TableColumn<Appointment, LocalDateTime> endColumn;
    @FXML
    private TableColumn<Appointment, Integer> customerIdColumn;
    @FXML
    private RadioButton viewByWeekRadioButton;
    @FXML
    private RadioButton viewByMonthRadioButton;
    @FXML
    private RadioButton viewAllRadioButton;
    @FXML
    private ToggleGroup viewToggleGroup;

    @FXML
    void onActionViewAll(ActionEvent event)
    {
        appointmentTable.setItems(DBAppointment.getAllAppointments());
    }

    @FXML
    void onActionViewByMonth(ActionEvent event)
    {
        appointmentTable.setItems(DBAppointment.getMonthAppointments());
    }


    @FXML
    void onActionViewByWeek(ActionEvent event)
    {
        appointmentTable.setItems(DBAppointment.getWeekAppointments());
    }


    @FXML
    void onActionDeleteAppointment(ActionEvent event) throws IOException
    {
        if (appointmentTable.getSelectionModel().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("PLEASE SELECT AN APPOINTMENT");
            alert.setContentText("No appointment was selected to delete.");

            Optional<ButtonType> result = alert.showAndWait();
        }
        else
        {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("ARE YOU SURE?");
            alert.setContentText("The appointment will be deleted from the database, are you sure you want to continue? This action cannot be undone.");

            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK)
            {
                int appointmentId = appointmentTable.getSelectionModel().getSelectedItem().getAppointmentId();

                String typeMessage = appointmentTable.getSelectionModel().getSelectedItem().getType();

                String idMessage = String.valueOf(appointmentTable.getSelectionModel().getSelectedItem().getAppointmentId());

                DBAppointment.deleteAppointment(appointmentId);

                appointmentTable.setItems(DBAppointment.getAllAppointments());

                Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                alert2.setHeaderText("DELETED");
                alert2.setContentText("You have successfully deleted appointment " + idMessage + ", a " + typeMessage + " appointment.");

                alert2.showAndWait();
            }
            else
            {
                Alert alert3 = new Alert(Alert.AlertType.INFORMATION);
                alert3.setHeaderText("NOT DELETED");
                alert3.setContentText("The selected appointment was not deleted.");

                alert3.showAndWait();
            }
        }
    }


    @FXML
    void onActionGoToAddAppointmentScreen(ActionEvent event) throws IOException
    {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("../view/AddAppointment.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }


    @FXML
    void onActionGoToUpdateAppointmentScreen(ActionEvent event) throws IOException
    {
        if (appointmentTable.getSelectionModel().isEmpty())
        {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("PLEASE SELECT AN APPOINTMENT");
            alert.setContentText("No appointment was selected to update.");

            Optional<ButtonType> result = alert.showAndWait();
        }

        else
        {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../view/UpdateAppointment.fxml"));
            loader.load();

            ModifyAppointment ADMController = loader.getController();
            ADMController.sendAppointment(appointmentTable.getSelectionModel().getSelectedItem());


            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }


    @FXML
    void onActionGoToMainMenu(ActionEvent event) throws IOException
    {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("../view/MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        viewAllRadioButton.setSelected(true);

        appointmentIdColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        contactColumn.setCellValueFactory(new PropertyValueFactory<>("contactName"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        startColumn.setCellValueFactory(new PropertyValueFactory<>("start"));
        endColumn.setCellValueFactory(new PropertyValueFactory<>("end"));
        customerIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));

        appointmentTable.setItems(DBAppointment.getAllAppointments());
    }
}
