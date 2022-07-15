package controller;

import DAO.DBCountries;
import DAO.DBCustomers;
import DAO.DBFLDivisions;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
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

/**
 * FXML Controller class
 *
 * @author Kane
 */
public class ModifyCustomerFormController implements Initializable {

    @FXML
    private Label CustomerIDLabel;

    @FXML
    private Label NameLabel;

    @FXML
    private Label Address1Label;

    @FXML
    private Label FLDivisionLabel;

    @FXML
    private Label PostalCodeLabel;

    @FXML
    private Label CountryLabel;

    @FXML
    private Label PhoneNumberLabel;

    @FXML
    private TextField CustomerIDTextField;

    @FXML
    private TextField NameTextField;

    @FXML
    private TextField Address1TextField;

    @FXML
    private ComboBox<FirstLevelDivisions> FLDivisionComboBox;

    @FXML
    private TextField PostalCodeTextField;

    @FXML
    private ComboBox<Countries> CountryComboBox;

    @FXML
    private TextField PhoneNumberTextField;

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

    @FXML
    private TableColumn<Customers, String> Address1Column;

    @FXML
    private TableColumn<Customers, String> FLDivisionColumn;

    @FXML
    private TableColumn<Customers, String> PostalCodeColumn;

    @FXML
    private TableColumn<Customers, String> CountryColumn;

    @FXML
    private TableColumn<Customers, String> PhoneNumberColumn;

    private ObservableList<Customers> setCustomers;


    public static int id;


    public Customers getCustomer(Customers customer) throws SQLException {
        Customers getCustomer = customer;
        Countries c = DBCountries.getCountryByDivisionID(getCustomer.getDivisionID());
        ObservableList<Countries> countries = DBCountries.getAllCountries();
        ObservableList<FirstLevelDivisions> flDivision = DBFLDivisions.getDivisionsByCountryID(c.getCountryID());

        CustomerIDTextField.setText(String.valueOf(getCustomer.getCustomerID()));
        NameTextField.setText(getCustomer.getCustomerName());
        Address1TextField.setText(getCustomer.getAddress());
        PostalCodeTextField.setText(getCustomer.getPostalCode());

        //lambda 2 to set the first level divisions for the modify page. Lambda 1 is in the MainScreenController.java at line 370.
        FLDivisionComboBox.setItems(flDivision);
        flDivision.forEach(FirstLevelDivisions -> {
            if(FirstLevelDivisions.getDivisionID() == customer.getDivisionID())
                FLDivisionComboBox.setValue(FirstLevelDivisions);
        });

        CountryComboBox.setItems(countries);
        CountryComboBox.setValue(c);
        PhoneNumberTextField.setText(getCustomer.getPhoneNumber());

        return getCustomer;
    }


    @FXML
    void onActionSelectCountry(ActionEvent event) {
        int countryID = CountryComboBox.getValue().getCountryID();
        try {
            FLDivisionComboBox.setItems(DBFLDivisions.getDivisionsByCountryID(countryID));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    @FXML
    void onActionSelectFLD(ActionEvent event) {

    }


    @FXML
    void onActionSave(ActionEvent event) throws IOException {
        int customerID = Integer.parseInt(CustomerIDTextField.getText());
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

        DBCustomers.updateCustomer(customerName, address, postalCode, phoneNumber, divisionID, customerID);
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Parent scene = FXMLLoader.load(getClass().getResource("/View/MainScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.setTitle("Main");
        stage.show();
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