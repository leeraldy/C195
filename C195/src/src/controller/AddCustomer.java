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
import model.Division;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;


public class AddCustomer implements Initializable
{
    Stage stage;
    Parent scene;

    @FXML
    private TextField customerIdText;

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
    void onActionAddCustomer(ActionEvent event) throws IOException
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("ARE YOU SURE?");
        alert.setContentText("The new customer will be added to the database, are you sure you want to continue?");

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
                DBCustomer.addCustomer(customerName, address, postalCode, phone, division.getDivisionId());


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
    void onActionCancelAdd(ActionEvent event) throws IOException
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("ARE YOU SURE?");
        alert.setContentText("This will clear all fields and cancel adding a customer, are you sure you want to continue?");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK)
        {
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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        countryComboBox.setItems(DBCountry.getAllCountries());
        divisionComboBox.getSelectionModel().clearSelection();
    }
}