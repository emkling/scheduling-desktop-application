package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Customer;
import util.Alerts;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static controller.MainViewController.loadMainView;
import static model.CountryDAO.getCountryNameList;
import static model.CustomerDAO.customerIdGenerator;
import static model.CustomerDAO.insertCustomerRow;
import static model.DivisionDAO.getDivisionIdFromState;
import static model.DivisionDAO.getStateList;
import static model.UserDAO.getCurrentUser;


/**
 * Controller class capable of adding new customer to the databases
 *
 */
public class AddCustomerController implements Initializable {


        @FXML
        private TextField AddTextField;

        @FXML
        private ComboBox<String> countryComboBox;

        @FXML
        private TextField nameTextField;

        @FXML
        private TextField phoneTextField;

        @FXML
        private TextField postalTextField;

        @FXML
        private ComboBox<String> stateComboBox;


        /**
         * Cancels window and returns to the MainViewController
         *
         * @param event Cancel button action
         * @throws IOException if initialization error of scene occurs
         */
        @FXML
        void onActionCancelBtn(ActionEvent event) throws IOException {

             if (Alerts.confirmAlert("Confirm", "Are you sure you want to cancel?")) {

             Stage stage = ((Stage)((Button)event.getSource()).getScene().getWindow());
             loadMainView(stage);
             }
        }

        /**
         * Saves the user inputs of a new customer into the database
         *
         * @param event Save button action
         */
        @FXML
        void onActionSaveBtn(ActionEvent event) {

                try{
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
                            }
                            else {
                                Customer customer = new Customer(customerIdGenerator(), name, address, postal, phone, getDivisionIdFromState(stateSelection));

                                insertCustomerRow(customer, getCurrentUser().getUserName());

                                Stage stage = ((Stage) ((Button) event.getSource()).getScene().getWindow());
                                loadMainView(stage);
                            }
                        }
                }
                catch (Exception e){
                        Alerts.errorAlert("Error", "Customer Could not be saved");
                }
        }


        /**
         * Sets the lists for following combo boxes
         *
         * @param event Combo box action
         * @throws SQLException if SQL error occurs from retrieval attempt
         */
        @FXML
        void onActionComboBox(ActionEvent event) throws SQLException {
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

        /**
         * Initializes controller
         *
         * @param url Relative path for root object
         * @param resourceBundle Resources used to localize root object
         */
        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {
                try {
                        countryComboBox.setItems(getCountryNameList());
                } catch (SQLException e) {
                        e.printStackTrace();
                }

        }
}
