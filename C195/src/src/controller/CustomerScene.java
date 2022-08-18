package controller;

import DAO.DBCustomer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Customer;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;




public class CustomerScene implements Initializable
{
    Stage stage;
    Parent scene;


    @FXML
    private TableView<Customer> customerTable;
    @FXML
    private TableColumn<Customer, Integer> idColumn;
    @FXML
    private TableColumn<Customer, String> nameColumn;
    @FXML
    private TableColumn<Customer, String> addressColumn;
    @FXML
    private TableColumn<Customer, Integer> divisionColumn;
    @FXML
    private TableColumn<Customer, String> postalCodeColumn;
    @FXML
    private TableColumn<Customer, String> phoneColumn;

    @FXML
    void onActionDeleteCustomer(ActionEvent event) throws IOException {
        if (customerTable.getSelectionModel().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("PLEASE SELECT A CUSTOMER.");
            alert.setContentText("No customer was selected to delete.");

            Optional<ButtonType> result = alert.showAndWait();
        }

        else
        {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("ARE YOU SURE?");
            alert.setContentText("The customer will be deleted from the database, are you sure you want to continue? This action cannot be undone.");

            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK)
            {
                int customerId = customerTable.getSelectionModel().getSelectedItem().getCustomerId();

                DBCustomer.deleteCustomer(customerId);

                customerTable.setItems(DBCustomer.getAllCustomers());


                Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                alert2.setHeaderText("DELETED");
                alert2.setContentText("The selected customer was successfully deleted.");

                alert2.showAndWait();
            }
            else
            {
                Alert alert3 = new Alert(Alert.AlertType.INFORMATION);
                alert3.setHeaderText("NOT DELETED");
                alert3.setContentText("The selected customer was not deleted.");

                alert3.showAndWait();
            }
        }
    }


    @FXML
    void onActionGoToAddCustomerScreen(ActionEvent event) throws IOException
    {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("../view/AddCustomer.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }


    @FXML
    void onActionGoToUpdateCustomerScreen(ActionEvent event) throws IOException {

        if (customerTable.getSelectionModel().isEmpty())
        {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("PLEASE SELECT A CUSTOMER.");
            alert.setContentText("No customer was selected to update.");

            Optional<ButtonType> result = alert.showAndWait();
        }

        else
        {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../view/UpdateCustomer.fxml"));
            loader.load();

            ModifyCustomer ADMController = loader.getController();
            ADMController.sendCustomer(customerTable.getSelectionModel().getSelectedItem());

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
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        divisionColumn.setCellValueFactory(new PropertyValueFactory<>("divisionName"));
        postalCodeColumn.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));

        customerTable.setItems(DBCustomer.getAllCustomers());
    }
}
