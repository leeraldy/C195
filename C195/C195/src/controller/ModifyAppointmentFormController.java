package controller;

import DAO.DBAppointments;
import DAO.DBContacts;
import DAO.DBCustomers;
import DAO.DBUsers;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.TimeZone;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointments;
import model.Contacts;
import model.Customers;
import model.Users;


public class ModifyAppointmentFormController implements Initializable {

    @FXML
    private Label AppointmentIDLabel;

    @FXML
    private Label UserIDLabel;

    @FXML
    private Label CustomerIDLabel;

    @FXML
    private Label TitleLabel;

    @FXML
    private Label DescriptionLabel;

    @FXML
    private Label LocationLabel;

    @FXML
    private Label ContactLabel;

    @FXML
    private Label TypeLabel;

    @FXML
    private Label StartDateLabel;

    @FXML
    private Label StartTimeLabel;

    @FXML
    private Label EndDateLabel;

    @FXML
    private Label EndTime;

    @FXML
    private TextField AppointmentDTextField;

    @FXML
    private ComboBox<Integer> UserIDComboBox;

    @FXML
    private ComboBox<Integer> CustomerComboBox;

    @FXML
    private TextField TitleTextField;

    @FXML
    private TextField DescriptionTextField;

    @FXML
    private TextField LocationTextField;

    @FXML
    private ComboBox<Integer> ContactComboBox;

    @FXML
    private TextField TypeTextField;

    @FXML
    private DatePicker StartDatePicker;

    @FXML
    private ComboBox<String> StartTimeComboBox;

    @FXML
    private DatePicker EndDatePicker;

    @FXML
    private ComboBox<String> EndTimeComboBox;

    @FXML
    private Button SaveButton;

    @FXML
    private Button CancelButton;

    @FXML
    private TableView<Customers> CustomerTableView;

    @FXML
    private TableColumn<Customers, Integer> CustomerIDColumn;

    @FXML
    private TableColumn<Customers, String> NameColumn;

    private ObservableList<Customers> setCustomers;

    private Appointments getAppointment;

    int index;


    public static int idNum;

    private ZoneId zoneID = ZoneId.of("UTC");
    private ZoneId zoneIDEST = ZoneId.of("America/New_York");
    private ZoneId zoneIDDef = ZoneId.of(TimeZone.getDefault().getID());


    public Appointments getAppointment(Appointments appointment) {
        Appointments getAppointment = appointment;
        try {

            String startZID = appointment.getStart().atZone(zoneIDDef).format(DateTimeFormatter.ofPattern("HH:mm"));
            String endZID = appointment.getEnd().atZone(zoneIDDef).format(DateTimeFormatter.ofPattern("HH:mm"));

            AppointmentDTextField.setText(String.valueOf(getAppointment.getAppointmentID()));
            UserIDComboBox.getSelectionModel().select(getAppointment.getUserID() - 1);
            CustomerComboBox.getSelectionModel().select(getAppointment.getCustomerID() - 1);
            TitleTextField.setText(getAppointment.getTitle());
            DescriptionTextField.setText(String.valueOf(getAppointment.getDescription()));
            LocationTextField.setText(getAppointment.getLocation());
            ContactComboBox.getSelectionModel().select(getAppointment.getContactID() - 1);
            TypeTextField.setText(getAppointment.getType());
            StartDatePicker.setValue(getAppointment.getStart().toLocalDate());
            StartTimeComboBox.getSelectionModel().select(String.valueOf(startZID));
            EndDatePicker.setValue(getAppointment.getEnd().toLocalDate());
            EndTimeComboBox.getSelectionModel().select(String.valueOf(endZID));

        } catch (Exception throwables) {
            throwables.printStackTrace();
        }
        return getAppointment;
    }


    @FXML
    void onActionSave(ActionEvent event) throws SQLException, IOException {


        comboBoxAlert();
        try {
            int appointmentID = Integer.parseInt(AppointmentDTextField.getText());
            int userID = UserIDComboBox.getValue();
            int customerID = CustomerComboBox.getValue();
            String title = TitleTextField.getText();
            String description = DescriptionTextField.getText();
            String location = LocationTextField.getText();
            int contactID = ContactComboBox.getValue();
            String type = TypeTextField.getText();
            LocalDateTime start = LocalDateTime.of(StartDatePicker.getValue(), LocalTime.parse(StartTimeComboBox.getSelectionModel().getSelectedItem()));
            LocalDateTime end = LocalDateTime.of(EndDatePicker.getValue(), LocalTime.parse(EndTimeComboBox.getSelectionModel().getSelectedItem()));;
            ZonedDateTime startUTC = start.atZone(zoneID).withZoneSameInstant(ZoneId.of("UTC"));
            ZonedDateTime endUTC = end.atZone(zoneID).withZoneSameInstant(ZoneId.of("UTC"));
            Timestamp startTS = Timestamp.valueOf(startUTC.toLocalDateTime());
            Timestamp endTS = Timestamp.valueOf(endUTC.toLocalDateTime());

            ZonedDateTime businessStartEST = ZonedDateTime.of(start, zoneIDEST);
            ZonedDateTime businessEndEST = ZonedDateTime.of(end, zoneIDEST);

            if (businessStartEST.toLocalTime().isAfter(LocalTime.of(22, 0)) || businessEndEST.toLocalTime().isAfter(LocalTime.of(22, 0))
                    || businessStartEST.toLocalTime().isBefore(LocalTime.of(8, 0)) || businessEndEST.toLocalTime().isBefore(LocalTime.of(8, 0))) {
                //appointmentAlertsEN(3);
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("WARNING");
                alert.setHeaderText("Warning: Outside Business Hours");
                alert.setContentText("Please select a time within business hours.");
                alert.showAndWait();
                return;
            };

            ObservableList<Appointments> appointment = DBAppointments.getAppointmentsByCustomerID(CustomerComboBox.getSelectionModel().getSelectedItem());
            for (Appointments appt : appointment) {

                LocalDateTime apptSt = appt.getStart();
                LocalDateTime apptEnd = appt.getEnd();
                Timestamp apptStTS = Timestamp.valueOf(apptSt);
                Timestamp apptEndTS = Timestamp.valueOf(apptEnd);

                if (startTS.after(apptStTS) && startTS.before(apptEndTS)
                        || endTS.after(apptStTS) && endTS.before(apptEndTS)
                        || startTS.before(apptStTS) && endTS.after(apptStTS)
                        || startTS.equals(apptStTS) && endTS.equals(apptEndTS)
                        || startTS.equals(apptStTS) || endTS.equals(apptStTS)) {
                    //appointmentAlertsEN(2);
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("WARNING");
                    alert.setHeaderText("Warning: Overlapping Appointment");
                    alert.setContentText("Chosen appointment is overlapping with another appointment. Please select again.");
                    alert.showAndWait();
                    return;
                }
                if (endTS.before(startTS)) {
                    //appointmentAlertsEN(5);
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("WARNING");
                    alert.setHeaderText("Warning: Invalid Appointment Time Or Date");
                    alert.setContentText("Appointment start time cannot be before the end time. Appointments must be start and end on the same day.");
                    alert.showAndWait();
                    return;
                }
            }

            if (AppointmentDTextField.getText().isEmpty() || TitleTextField.getText().isEmpty()
                    || DescriptionTextField.getText().isEmpty() || LocationTextField.getText().isEmpty()
                    || TypeTextField.getText().isEmpty()) {
                //appointmentAlertsEN(4);
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("WARNING");
                alert.setHeaderText("Warning: Blank Fields");
                alert.setContentText("One or more fields have been left blank. Please fill them and try again.");
                alert.showAndWait();

            } else {

                DBAppointments.updateAppointments(title, description, location, type, startTS, endTS, customerID, userID, contactID, appointmentID);

                Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                Parent scene = FXMLLoader.load(getClass().getResource("/View/MainScreen.fxml"));
                stage.setScene(new Scene(scene));
                stage.setTitle("Main");
                stage.show();

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    public void comboBoxAlert() {

        if (UserIDComboBox.getSelectionModel().isEmpty() || CustomerComboBox.getSelectionModel().isEmpty()
                || ContactComboBox.getSelectionModel().isEmpty() || StartDatePicker.getValue() == null
                || StartTimeComboBox.getSelectionModel().isEmpty() || EndDatePicker.getValue() == null
                || EndTimeComboBox.getSelectionModel().isEmpty()) {
            //appointmentAlertsEN(4);
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("WARNING");
            alert.setHeaderText("Warning: Blank Fields");
            alert.setContentText("One or more fields have been left blank. Please fill them and try again.");
            alert.showAndWait();
        }
    }


    public void selectUserID() {
        ObservableList<Integer> userIDCombo = FXCollections.observableArrayList();

        try {
            ObservableList<Users> selectUser = DBUsers.getAllUsers();
            if (selectUser != null) {
                for (Users user : selectUser) {
                    userIDCombo.add(user.getUserID());
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        UserIDComboBox.setItems(userIDCombo);
    }

    public void selectCustomerID() {
        ObservableList<Integer> customerIDCombo = FXCollections.observableArrayList();

        try {
            ObservableList<Customers> selectCustomer = DBCustomers.getAllCustomers();
            if (selectCustomer != null) {
                for (Customers cust : selectCustomer) {
                    customerIDCombo.add(cust.getCustomerID());
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        CustomerComboBox.setItems(customerIDCombo);
    }


    public void selectContact() {
        ObservableList<Integer> contactCombo = FXCollections.observableArrayList();

        ObservableList<Contacts> selectContact = DBContacts.getAllContacts();
        if (selectContact != null) {
            for (Contacts cont : selectContact) {
                contactCombo.add(cont.getContactID());
            }
        }
        ContactComboBox.setItems(contactCombo);
    }

    @FXML
    void onActionSelectStartDate(ActionEvent event) {

    }

    @FXML
    void onActionSelectEndDate(ActionEvent event) {

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
        try {
            setCustomers = DBCustomers.getAllCustomers();

            CustomerTableView.setItems(setCustomers);
            CustomerIDColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));
            NameColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));

        } catch (Exception throwables) {
            throwables.printStackTrace();
        }
        selectUserID();
        selectCustomerID();
        selectContact();

        ObservableList<String> time = FXCollections.observableArrayList();
        LocalTime start = LocalTime.of(7, 0);
        LocalTime end = LocalTime.of(23, 0);

        time.add(start.toString());
        while (start.isBefore(end)) {
            start = start.plusMinutes(15);
            time.add(start.toString());
        }
        StartTimeComboBox.setItems(time);
        EndTimeComboBox.setItems(time);
        AppointmentDTextField.setText(Integer.toString(idNum));

    }

}