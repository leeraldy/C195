package controller;

import DAO.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.*;
import java.util.Optional;
import java.util.ResourceBundle;



public class ModifyAppointment implements Initializable
{

    Stage stage;
    Parent scene;


    @FXML
    private TableView<Customer> customerTable;

    @FXML
    private TableColumn<Customer, Integer> customerIdColumn;

    @FXML
    private TableColumn<Customer, String> customerNameColumn;

    @FXML
    private TextField appointmentIdText;

    @FXML
    private TextField titleText;

    @FXML
    private TextField descriptionText;

    @FXML
    private TextField locationText;

    @FXML
    private ComboBox<Contact> contactComboBox;

    @FXML
    private TextField typeText;

    @FXML
    private ComboBox<LocalTime> startTimeComboBox;

    @FXML
    private ComboBox<LocalTime> endTimeComboBox;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextField customerId;

    @FXML
    private ComboBox<User> userIdComboBox;


    @FXML
    void onMouseClickFillCustomerTextField(MouseEvent event)
    {
        customerId.setText(String.valueOf(customerTable.getSelectionModel().getSelectedItem().getCustomerId()));
    }



    @FXML
    void onActionUpdateAppointment(ActionEvent event) throws IOException
    {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("ARE YOU SURE?");
        alert.setContentText("The new appointment will be added to the calendar, are you sure you want to continue?");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK)
        {
            String customer_Id = customerId.getText();
            String title = titleText.getText();
            String description = descriptionText.getText();
            String location = locationText.getText();
            Contact contact = contactComboBox.getValue();
            String type = typeText.getText();
            LocalDate date = datePicker.getValue();

            LocalTime st = startTimeComboBox.getValue();
            LocalTime et = endTimeComboBox.getValue();
            User userId = userIdComboBox.getValue();
            int appointment_Id = Integer.parseInt(appointmentIdText.getText());

            if (contact!=null && !type.isEmpty() && date!=null && st!=null && et!=null && !customer_Id.isEmpty() && userId!=null)
            {

                Timestamp start = Timestamp.valueOf(LocalDateTime.of( date, startTimeComboBox.getValue()));
                Timestamp end = Timestamp.valueOf(LocalDateTime.of( date, endTimeComboBox.getValue()));
                int cId = Integer.parseInt(customer_Id);

                if (LocalDateTime.of(date, endTimeComboBox.getValue()).isAfter(LocalDateTime.of(date, startTimeComboBox.getValue())))
                {
                    Appointment newAppointment = new Appointment(appointment_Id, title, description, location, contact.getContactId(), contact.getContactName(), type, start, end, cId, userId.getUserId());

                    if (DBAppointment.checkForOverlappingAppointment(newAppointment))
                    {

                        Alert alert3 = new Alert(Alert.AlertType.ERROR);
                        alert3.setHeaderText("APPOINTMENT OVERLAP");
                        alert3.setContentText("Appointment overlaps with an existing appointment for the selected customer.");
                        alert3.showAndWait();
                    }
                    else {

                        DBAppointment.updateAppointment(title, description, location, type, start, end, cId, userId.getUserId(), contact.getContactId(), appointment_Id);

                        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                        scene = FXMLLoader.load(getClass().getResource("../view/AppointmentScene.fxml"));
                        stage.setScene(new Scene(scene));
                        stage.show();
                    }
                }
                else
                {
                    Alert alert3 = new Alert(Alert.AlertType.ERROR);
                    alert3.setHeaderText("TIME ERROR");
                    alert3.setContentText("Appointment end time must be after appointment start time.");
                    alert3.showAndWait();
                }

            }
            else
            {
                Alert alert3 = new Alert(Alert.AlertType.ERROR);
                alert3.setHeaderText("INVALID ENTRIES");
                alert3.setContentText("Please enter a valid value for each required field!");
                alert3.showAndWait();
            }
        }
    }


    @FXML
    void onActionCancelUpdate(ActionEvent event) throws IOException
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("ARE YOU SURE?");
        alert.setContentText("Are you sure you want to cancel updating the appointment?");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("../view/AppointmentScene.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }



    Appointment appointment;


    public void sendAppointment(Appointment appointment)
    {

        this.appointment = appointment;

        appointmentIdText.setText(Integer.toString(appointment.getAppointmentId()));
        titleText.setText(appointment.getTitle());
        descriptionText.setText(appointment.getDescription());
        locationText.setText(appointment.getLocation());

        for (Contact c : contactComboBox.getItems()) {
            if (appointment.contactId == c.getContactId()) {
                contactComboBox.setValue(c);
                break;
            }
        }

        typeText.setText(appointment.getType());

        LocalTime setStart = appointment.getStart().toLocalDateTime().toLocalTime();
        startTimeComboBox.setValue(setStart);
        LocalTime setEnd = appointment.getEnd().toLocalDateTime().toLocalTime();
        endTimeComboBox.setValue(setEnd);

        LocalDate appointmentDate = appointment.getStart().toLocalDateTime().toLocalDate();
        datePicker.setValue(appointmentDate);

        customerId.setText(String.valueOf(appointment.getCustomerId()));

        for (User u : userIdComboBox.getItems())
        {
            if (appointment.userId == u.getUserId())
            {
                userIdComboBox.setValue(u);
                break;
            }
        }
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        customerIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        customerNameColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));

        customerTable.setItems(DBCustomer.getAllCustomers());

        contactComboBox.setItems(DBContact.getAllContacts());
        userIdComboBox.setItems(DBUser.getAllUsers());

        LocalTime appointmentStartTimeMinEST = LocalTime.of(8, 0);
        LocalDateTime startMinEST = LocalDateTime.of(LocalDate.now(), appointmentStartTimeMinEST);
        ZonedDateTime startMinZDT = startMinEST.atZone(ZoneId.of("America/New_York"));
        ZonedDateTime startMinLocal = startMinZDT.withZoneSameInstant(ZoneId.systemDefault());
        LocalTime appointmentStartTimeMin = startMinLocal.toLocalTime();

        LocalTime appointmentStartTimeMaxEST = LocalTime.of(21, 45);
        LocalDateTime startMaxEST = LocalDateTime.of(LocalDate.now(), appointmentStartTimeMaxEST);
        ZonedDateTime startMaxZDT = startMaxEST.atZone(ZoneId.of("America/New_York"));
        ZonedDateTime startMaxLocal = startMaxZDT.withZoneSameInstant(ZoneId.systemDefault());
        LocalTime appointmentStartTimeMax = startMaxLocal.toLocalTime();

        while (appointmentStartTimeMin.isBefore(appointmentStartTimeMax.plusSeconds(1)))
        {
            startTimeComboBox.getItems().add(appointmentStartTimeMin);
            appointmentStartTimeMin = appointmentStartTimeMin.plusMinutes(15);
        }

        LocalTime appointmentEndTimeMinEST = LocalTime.of(8, 15);
        LocalDateTime endMinEST = LocalDateTime.of(LocalDate.now(), appointmentEndTimeMinEST);
        ZonedDateTime endMinZDT = endMinEST.atZone(ZoneId.of("America/New_York"));
        ZonedDateTime endMinLocal = endMinZDT.withZoneSameInstant(ZoneId.systemDefault());
        LocalTime appointmentEndTimeMin = endMinLocal.toLocalTime();

        LocalTime appointmentEndTimeMaxEST = LocalTime.of(22, 0);
        LocalDateTime endMaxEST = LocalDateTime.of(LocalDate.now(), appointmentEndTimeMaxEST);
        ZonedDateTime endMaxZDT = endMaxEST.atZone(ZoneId.of("America/New_York"));
        ZonedDateTime endMaxLocal = endMaxZDT.withZoneSameInstant(ZoneId.systemDefault());
        LocalTime appointmentEndTimeMax = endMaxLocal.toLocalTime();

        while (appointmentEndTimeMin.isBefore(appointmentEndTimeMax.plusSeconds(1)))
        {
            endTimeComboBox.getItems().add(appointmentEndTimeMin);
            appointmentEndTimeMin = appointmentEndTimeMin.plusMinutes(15);
        }
    }
}