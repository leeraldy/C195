package controller;

import DAO.DBCountry;
import DAO.DBCustomer;
import DAO.DBDivision;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Country;
import model.Customer;
import model.Division;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;



public class ModifyCustomer implements Initializable
{
    Stage stage;
    Parent scene;


    @FXML
    private TextField idText;

    @FXML
    private TextField nameText;

    @FXML
    private TextField addressText;

    @FXML
    private ComboBox<Country> countryComboBox;

    @FXML
    private ComboBox<Division> divisionComboBox;

    @FXML
    private TextField postalCodeText;

    @FXML
    private TextField phoneText;

    @FXML
    private Label divisionSwitchLabel;


    @FXML
    void onActionUpdateCustomer(ActionEvent event) throws IOException
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("ARE YOU SURE?");
        alert.setContentText("The customer will be updated in the database, are you sure you want to continue?");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK)
        {

            String customerName = nameText.getText();
            String address = addressText.getText();
            String postalCode = postalCodeText.getText();
            String phone = phoneText.getText();
            Division division = divisionComboBox.getValue();

            if (!customerName.isEmpty() && !address.isEmpty() && !postalCode.isEmpty() && !phone.isEmpty() && !(division == null))
            {
                DBCustomer.updateCustomer(customerName, address, postalCode, phone, division.getDivisionId(), customer.getCustomerId());

                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("../view/CustomerScene.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
            }
            else
            {
                Alert alert3 = new Alert(Alert.AlertType.ERROR);
                alert3.setHeaderText("BLANK FIELDS");
                alert3.setContentText("Please enter a valid value for each field! All fields are required.");
                alert3.showAndWait();
            }
        }
    }


    @FXML
    void onActionCancelUpdate(ActionEvent event) throws IOException
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("ARE YOU SURE?");
        alert.setContentText("This will clear all fields and cancel updating a customer, are you sure you want to continue?");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("../view/CustomerScene.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }

    }


    @FXML
    void onActionDivision(ActionEvent event)
    {

        Country country = countryComboBox.getSelectionModel().getSelectedItem();

        if (country.getCountryId() == 1)
        {
            divisionSwitchLabel.setText("State:");
        }
        else if (country.getCountryId() == 2)
        {
            divisionSwitchLabel.setText("Sub-division:");
        }
        else if (country.getCountryId() == 3)
        {
            divisionSwitchLabel.setText("Province:");
        }

        if (country.getCountryId() == 1)
        {
            divisionComboBox.setItems(DBDivision.getUSDivisions());
        }
        else if (country.getCountryId() == 2)
        {
            divisionComboBox.setItems(DBDivision.getUKDivisions());
        }
        else if (country.getCountryId() == 3)
        {
            divisionComboBox.setItems(DBDivision.getCADivisions());
        }
        else
        {
            divisionComboBox.isDisabled();
        }
    }


    Customer customer;


    public void sendCustomer(Customer customer) {

        this.customer = customer;

        idText.setText(Integer.toString(customer.getCustomerId()));
        nameText.setText(customer.getCustomerName());
        addressText.setText(customer.getAddress());

        for (Country c : countryComboBox.getItems())
        {
            if(customer.countryId == c.getCountryId())
            {
                countryComboBox.setValue(c);
                break;
            }
        }

        Country country = countryComboBox.getSelectionModel().getSelectedItem();

        if (country.getCountryId() == 1)
        {
            divisionSwitchLabel.setText("State:");
        }
        else if (country.getCountryId() == 2)
        {
            divisionSwitchLabel.setText("Sub-division:");
        }
        else if (country.getCountryId() == 3)
        {
            divisionSwitchLabel.setText("Province:");
        }

        if (country.getCountryId() == 1)
        {
            divisionComboBox.setItems(DBDivision.getUSDivisions());
        }
        else if (country.getCountryId() == 2)
        {
            divisionComboBox.setItems(DBDivision.getUKDivisions());
        }
        else if (country.getCountryId() == 3)
        {
            divisionComboBox.setItems(DBDivision.getCADivisions());
        }
        else
        {
            divisionComboBox.isDisabled();
        }

        for(Division d : divisionComboBox.getItems())
        {
            if(customer.divisionId == d.getDivisionId())
            {
                divisionComboBox.setValue(d);
                break;
            }
        }
        postalCodeText.setText(customer.getPostalCode());
        phoneText.setText(customer.getPhone());
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        countryComboBox.setItems(DBCountry.getAllCountries());
    }
}