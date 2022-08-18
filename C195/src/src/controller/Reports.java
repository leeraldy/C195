package controller;

import DAO.DBAppointment;
import DAO.DBContact;
import DAO.DBCustomer;
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
import model.Appointment;
import model.Contact;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;



public class Reports implements Initializable
{
    Stage stage;
    Parent scene;


    @FXML
    private ComboBox<String> monthComboBox;

    @FXML
    private ComboBox<String> typeComboBox;

    @FXML
    private Label report1ResultsLabel;

    @FXML
    private TableView<Appointment> report2;

    @FXML
    private TableColumn<Appointment, Integer> appointmentIdColumnReport2;

    @FXML
    private TableColumn<Appointment, String> appointmentTitleColumnReport2;

    @FXML
    private TableColumn<Appointment, String> appointmentTypeColumnReport2;

    @FXML
    private TableColumn<Appointment, String> appointmentDescriptionColumnReport2;

    @FXML
    private TableColumn<Appointment, LocalDateTime> startColumnReport2;

    @FXML
    private TableColumn<Appointment, LocalDateTime> endColumnReport2;

    @FXML
    private TableColumn<Appointment, Integer> customerIdColumnReport2;

    @FXML
    private ComboBox<Contact> contactComboBox;

    @FXML
    private Label report3ResultsLabel;

    @FXML
    void onActionRunReport1(ActionEvent event)
    {
        String month = monthComboBox.getValue();
        if (month == null)
        {
            return;
        }

        String type = typeComboBox.getValue();
        if (type == null)
        {
            return;
        }

        int total = DBAppointment.getMonthAndTypeCount(month, type);

        report1ResultsLabel.setText(String.valueOf(total));
    }


    @FXML
    void onActionRunReport2(ActionEvent event)
    {
        Contact contact = contactComboBox.getValue();

        if (contact == null)
        {
            return;
        }
        ObservableList<Appointment> aList = DBAppointment.getAllAppointments();
        ObservableList<Appointment> cList = aList.filtered(ap ->
        {

            if (ap.getContactId() == contact.getContactId()) {
                return true;
            }
            return false;

        });

        report2.setItems(cList);
    }



    @FXML
    void onActionRunReport3(ActionEvent event)
    {
        report3ResultsLabel.setText(String.valueOf(DBCustomer.getAllCustomers().size()));
    }


    @FXML
    void onActionGoToMainMenu(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("../view/MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        appointmentIdColumnReport2.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        appointmentTitleColumnReport2.setCellValueFactory(new PropertyValueFactory<>("title"));
        appointmentDescriptionColumnReport2.setCellValueFactory(new PropertyValueFactory<>("description"));
        appointmentTypeColumnReport2.setCellValueFactory(new PropertyValueFactory<>("type"));
        startColumnReport2.setCellValueFactory(new PropertyValueFactory<>("start"));
        endColumnReport2.setCellValueFactory(new PropertyValueFactory<>("end"));
        customerIdColumnReport2.setCellValueFactory(new PropertyValueFactory<>("customerId"));

        monthComboBox.setItems(FXCollections.observableArrayList("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"));
        typeComboBox.setItems(DBAppointment.getAllTypes());

        contactComboBox.setItems(DBContact.getAllContacts());
    }
}