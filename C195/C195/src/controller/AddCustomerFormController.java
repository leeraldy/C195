package controller;

import DAO.DBCountries;
import DAO.DBCustomers;
import DAO.DBFLDivisions;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
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
import model.Countries;
import model.Customers;
import model.FirstLevelDivisions;


public class AddCustomerFormController implements Initializable {

    @FXML Label CustomerIDLabel;
    @FXML Label NameLabel;
    @FXML Label Address1Label;
    @FXML Label FLDivisionLabel;
    @FXML Label PostalCodeLabel;

    @FXML Label CountryLabel;

    @FXML Label PhoneNumberLabel;

    @FXML
     TextField CustomerIDTextField;

    @FXML
     TextField NameTextField;

    @FXML
     TextField Address1TextField;

    @FXML
     ComboBox<FirstLevelDivisions> FLDivisionComboBox;

    @FXML
     TextField PostalCodeTextField;

    @FXML
     ComboBox<Countries> CountryComboBox;
    @FXML TextField PhoneNumberTextField;

    @FXML
     Button SaveButton;

    @FXML
     Button CancelButton;

    @FXML
     TableView<Customers> CustomerTableView;

    @FXML
     TableColumn<Customers, Integer> CustomerIDColumn;

    @FXML
     TableColumn<Customers, String> NameColumn;

    @FXML
     TableColumn<Customers, String> Address1Column;

    @FXML
     TableColumn<Customers, String> FLDivisionColumn;

    @FXML
     TableColumn<Customers, String> PostalCodeColumn;

    @FXML
     TableColumn<Customers, String> CountryColumn;

    @FXML
     TableColumn<Customers, String> PhoneNumberColumn;

     ObservableList<Customers> setCustomers;

    public static int id;


    @FXML
    void onActionSave(ActionEvent event) throws IOException {
        int customerID = id++;
        String customerName = NameTextField.getText();
        String address = Address1TextField.getText();
        String postalCode = PostalCodeTextField.getText();
        String phoneNumber = PhoneNumberTextField.getText();
        FirstLevelDivisions flDivision = FLDivisionComboBox.getValue();
        int divisionID = flDivision.getDivisionID();
        Countries country = CountryComboBox.getValue();

        if (CustomerIDTextField.getText().isEmpty() || NameTextField.getText().isEmpty() || Address1TextField.getText().isEmpty() || FLDivisionComboBox.getSelectionModel().isEmpty()
                || PostalCodeTextField.getText().isEmpty() || CountryComboBox.getSelectionModel().isEmpty() || PhoneNumberTextField.getText().isEmpty()) {
            //appointmentAlertsEN(4);
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("WARNING");
            alert.setHeaderText("Warning: Blank Fields");
            alert.setContentText("One or more fields have been left blank. Please fill them and try again.");
            alert.showAndWait();
        }

        DBCustomers.addCustomer(customerName, address, postalCode, phoneNumber, divisionID);
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Parent scene = FXMLLoader.load(getClass().getResource("/View/MainScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.setTitle("Main");
        stage.show();
    }


    @FXML
    void onActionSelectCountry(ActionEvent event) throws SQLException {

        int countryID = CountryComboBox.getValue().getCountryID();
        try {
            FLDivisionComboBox.setItems(DBFLDivisions.getDivisionsByCountryID(countryID));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    @FXML
    void onActionSelectFLD(ActionEvent event) throws SQLException {

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
    public void initialize(URL url, ResourceBundle rb
    ) {
        // TODO
        CustomerIDTextField.setText(Integer.toString(id));

        //This is to populate the customer table view.
        try {
            setCustomers = DBCustomers.getAllCustomers();

            CustomerTableView.setItems(setCustomers);
            CustomerIDColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));
            NameColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
            Address1Column.setCellValueFactory(new PropertyValueFactory<>("address"));
            FLDivisionColumn.setCellValueFactory(new PropertyValueFactory<>("division"));
            PostalCodeColumn.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
            CountryColumn.setCellValueFactory(new PropertyValueFactory<>("country"));
            PhoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));

            CountryComboBox.setItems(DBCountries.getAllCountries());

        } catch (Exception throwables) {
            throwables.printStackTrace();
        }
    }

}