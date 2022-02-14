package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.CountryDAO;
import model.Customer;
import model.DivisionDAO;
import util.Alerts;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static controller.MainViewController.getSelectedCustomer;
import static model.CustomerDAO.updateCustomerRow;
import static model.DivisionDAO.*;
import static model.UserDAO.getCurrentUser;

/**
 * Controller class that allows for the update of customers in the database
 *
 */
public class ModifyCustomerController implements Initializable {

    @FXML
    private TextField AddTextField;

    @FXML
    private ComboBox<String> countryComboBox;

    @FXML
    private TextField custIdTextField;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField phoneTextField;

    @FXML
    private TextField postalTextField;

    @FXML
    private ComboBox<String> stateComboBox;


    /**
     * Reloads MainViewController
     *
     * @param event Cancel button action
     * @throws IOException if initialization error occurs
     */
    @FXML
    void onActionCancelBtn(ActionEvent event) throws IOException {
        if (Alerts.confirmAlert("Confirm", "Are you sure you want to cancel?")) {
            Stage stage = ((Stage)((Button)event.getSource()).getScene().getWindow());
            Parent scene = FXMLLoader.load(getClass().getResource("/view/MainView.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    /**
     * Sets the following combo boxes according to region
     *
     * @param event Combo box action
     * @throws SQLException if SQL error occurs from retrieval attempt
     */
    @FXML
    void onActionComboBox(ActionEvent event) throws SQLException {
        String countrySelection = countryComboBox.getValue();
        AddTextField.setText("");
        postalTextField.setText("");
        stateComboBox.setValue("");
        if (countrySelection.equals("U.S")) {
            stateComboBox.setItems(getStateList(1));
        }
        else if (countrySelection.equals("UK")) {
            stateComboBox.setItems(getStateList(2));
        }
        else if (countrySelection.equals("Canada")) {
            stateComboBox.setItems(getStateList(3));
        }
    }

    /**
     * Updates a customer in the database
     *
     * @param event Save button action
     */
    @FXML
    void onActionSaveBtn(ActionEvent event) throws SQLException, IOException {
        try {
            int id = getSelectedCustomer().getCustomerId();
            String name = nameTextField.getText();
            String address = AddTextField.getText();
            String postal = postalTextField.getText();
            String phone = phoneTextField.getText();
            String countrySelection = countryComboBox.getValue();
            String stateSelection = stateComboBox.getValue();

            if (name.isEmpty() || address.isEmpty() || postal.isEmpty() || phone.isEmpty()) {
                Alerts.errorAlert("Error", "Fields left blank");
            }
            else {
                if (countrySelection == null || stateSelection == null) {
                    Alerts.errorAlert("Error", "Please select the customer's country and state");
                } else {
                    if (!address.contains("Address")) {
                        address = getDivisionCountry(getDivisionIdFromState(stateSelection), address);
                    }

                    Customer customer = new Customer(id, name, address, postal, phone, getDivisionIdFromState(stateSelection));

                    updateCustomerRow(customer, getCurrentUser().getUserName(), id);

                    System.out.println(address);
                    System.out.print(stateSelection);

                    System.out.println(countrySelection);
                    Stage stage = ((Stage) ((Button) event.getSource()).getScene().getWindow());
                    Parent scene = FXMLLoader.load(getClass().getResource("/view/MainView.fxml"));
                    stage.setScene(new Scene(scene));
                    stage.show();
                }
            }
        }
            catch (Exception e){
            Alerts.errorAlert("Error", "Customer Could not be saved. Please ensure all fields are filled");
      }

    }

    /**
     * Initializes controller
     *
     * @param url Relative path for root object
     * @param resourceBundle Resources used to localize root object
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            String custAdd = getSelectedCustomer().getCustomerAddress();
            int sub = custAdd.indexOf(":")+2;

       //userLabel.setText("User: ");
       AddTextField.setText(getSelectedCustomer().getCustomerAddress().substring(sub));
       nameTextField.setText(getSelectedCustomer().getCustomerName());
       custIdTextField.setText(String.valueOf(getSelectedCustomer().getCustomerId()));
       phoneTextField.setText(getSelectedCustomer().getCustomerPhoneNumber());
       postalTextField.setText(getSelectedCustomer().getCustomerPostalCode());
       countryComboBox.setItems(CountryDAO.getCountryNameList());
       stateComboBox.setValue(DivisionDAO.getStateFromDivisionID(getSelectedCustomer().getCustomerDivisionId()));

          int countryValue = getDivisionCountry(getSelectedCustomer().getCustomerDivisionId());

          if (countryValue == 1) {
           countryComboBox.setValue("U.S");
          }
           else if (countryValue == 2) {
           countryComboBox.setValue("UK");
          }
          else if (countryValue == 3) {
           countryComboBox.setValue("Canada");
          }

          String countrySelection = countryComboBox.getValue();

          if (countrySelection.equals("U.S")) {
                stateComboBox.setItems(getStateList(1));
          }
          else if (countrySelection.equals("UK")) {
                stateComboBox.setItems(getStateList(2));
          }
          else if (countrySelection.equals("Canada")) {
                stateComboBox.setItems(getStateList(3));
          }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
