package controller;

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
import model.Customer;
import model.CustomerDAO;
import util.Alerts;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ResourceBundle;

import static model.AppointmentDAO.*;
import static model.CustomerDAO.getAllCustomers;
import static model.UserDAO.getCurrentUser;
import static util.Alerts.errorAlert;
import static util.Alerts.informationAlert;


/**
 * Main controller for the application
 *
 */
public class MainViewController implements Initializable {

    @FXML
    private Label userLabel;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TableView<Appointment> aptTable;

    @FXML
    private TableColumn<Appointment, String> aptContactColumn;

    @FXML
    private TableColumn<Appointment, Integer> aptCustIdColumn;

    @FXML
    private TableColumn<Appointment, String> aptDescriptionColumn;

    @FXML
    private TableColumn<Appointment, String> aptEndColumn;

    @FXML
    private TableColumn<Appointment, Integer> aptIdColumn;

    @FXML
    private TableColumn<Appointment, String> aptLocationColumn;

    @FXML
    private TableColumn<Appointment, String> aptStartColumn;

    @FXML
    private TableView<Customer> custTable;

    @FXML
    private TableColumn<Appointment, String> aptTitleColumn;

    @FXML
    private TableColumn<Appointment, String> aptTypeColumn;

    @FXML
    private TableColumn<Appointment, Integer> aptUserIdColumn;

    @FXML
    private TableColumn<Customer, String> custAddressColumn;

    @FXML
    private TableColumn<Customer, Integer> custDivisionColumn;

    @FXML
    private TableColumn<Customer, Integer> custIDColumn;

    @FXML
    private TableColumn<Customer, String> custNameColumn;

    @FXML
    private TableColumn<Customer, String> custPhoneColumn;

    @FXML
    private TableColumn<Customer, String> custPostalCodeColumn;

    private static Customer selectedCustomer;

    private static Appointment selectedAppointment;


    /**
     * Loads the AddAppointmentWindow
     *
     * @param event Add button action
     * @throws IOException if initialization error occurs
     */
    @FXML
    void onActionAptAddBtn(ActionEvent event) throws IOException {
        Stage stage = ((Stage)((Button)event.getSource()).getScene().getWindow());
        Parent scene = FXMLLoader.load(getClass().getResource("/view/AddAppointment.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Deletes the selected appointment from the database
     *
     * @param event Delete action button
     * @throws SQLException if SQL error occurs from retrieval attempt
     * @throws ParseException if time conversion error occurs
     */
    @FXML
    void onActionDeleteAptBtn(ActionEvent event) throws SQLException, ParseException {
        selectedAppointment = aptTable.getSelectionModel().getSelectedItem();

        if (selectedAppointment == null) {
            errorAlert("Error", "No appointment selected");
        }
        else {
               if  (Alerts.confirmAlert("Confirm", "Are you sure you want to cancel Appointment_ID: " + selectedAppointment.getAptId() + "; Type: " + selectedAppointment.getAptType() + "?")) {
                   deleteAppointment(selectedAppointment.getAptId());
                   aptTable.setItems(getAllAppointments());
                   informationAlert("Success", "The appointment was successfully deleted");
               }
        }
    }

    /**
     * Selects an appointment and loads the ModifyAppointmentController
     *
     * @param event
     * @throws IOException
     */
    @FXML
    void onActionAptModifyBtn(ActionEvent event) throws IOException {
        selectedAppointment = aptTable.getSelectionModel().getSelectedItem();

        if (selectedAppointment == null) {
            errorAlert("Error", "No appointment selected");
        }
        else {
            Stage stage = ((Stage)((Button)event.getSource()).getScene().getWindow());
            Parent scene = FXMLLoader. load(getClass().getResource("/view/ModifyAppointment.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }

    }

    /**
     * Sets the table to view all appointments
     *
     * @param event All radio button action
     * @throws SQLException if SQL error occurs from retrieval attempt
     * @throws ParseException if time conversion error occurs
     */
    @FXML
    void onActionAllRadioBtn(ActionEvent event) throws SQLException, ParseException {
        loadAllAptTable();
    }

    /**
     * Sets the table to view appointments by month
     *
     * @param event All radio button action
     * @throws SQLException if SQL error occurs from retrieval attempt
     * @throws ParseException if time conversion error occurs
     */
    @FXML
    void onActionMonthRadioBtn(ActionEvent event) throws SQLException, ParseException {
        try {

            if (datePicker.getValue() == null) {
                errorAlert("Error", "Please select a month from date picker");
            }
            else {
                loadAptMonthTable();
            }
        }
        catch (Exception e) {

        }
    }

    /**
     * Sets the table to view appointments by week
     *
     * @param event All radio button action
     * @throws SQLException if SQL error occurs from retrieval attempt
     * @throws ParseException if time conversion error occurs
     */
    @FXML
    void onActionWeekRadioBtn(ActionEvent event) throws SQLException, ParseException {
        if (datePicker.getValue() == null) {
            errorAlert("Error", "Please select a week from the date picker");
        }
        else {
            loadAptWeekTable();
        }
    }

    /**
     * Loads the add customer controller
     *
     * @param event Customer add button action
     * @throws IOException if initialization error occurs
     */
    @FXML
    void onActionCustAddBtn(ActionEvent event) throws IOException {
            Stage stage = ((Stage)((Button)event.getSource()).getScene().getWindow());
            Parent scene = FXMLLoader.load(getClass().getResource("/view/AddCustomer.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
    }

    /**
     * Deletes the selected customer from the database
     *
     * @param event Delete action button
     * @throws SQLException if SQL error aoccurs from retrieval attempt
     */
    @FXML
    void onActionCustDeleteBtn(ActionEvent event) throws SQLException, ParseException {
        selectedCustomer = custTable.getSelectionModel().getSelectedItem();

        if (selectedCustomer == null) {
            errorAlert("Error", "No customer selected");
        }
        else {
            if (getAppointmentsByCustomerId(selectedCustomer.getCustomerId()).size() > 0) {
                errorAlert("Error", "Customer appointments must be deleted before customer can be removed");
            }
            else
            {
                if (Alerts.confirmAlert("Confirm", "Are you sure you want to delete " + selectedCustomer.getCustomerName() + " from the database?"))
                {
                    CustomerDAO.deleteCustomerRow(selectedCustomer.getCustomerId());
                    custTable.setItems(getAllCustomers());
                    informationAlert("Success", "The customer was successfully deleted");
                }
            }
        }
    }

    /**
     * Selects customer and opens the ModifyCustomerController
     *
     * @param event
     * @throws IOException
     */
    @FXML
    void onActionCustModifyBtn(ActionEvent event) throws IOException {
        selectedCustomer = custTable.getSelectionModel().getSelectedItem();
        if (selectedCustomer == null) {
            errorAlert("Error", "No Customer Selected");
        }
        else {
            Stage stage = ((Stage)((Button)event.getSource()).getScene().getWindow());
            Parent scene = FXMLLoader.load(getClass().getResource("/view/modifyCustomer.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    /**
     * Exits the program
     *
     * @param event Exit button action
     */
    @FXML
    void onActionExitBtn(ActionEvent event) {
        if (Alerts.confirmAlert("Confirm", "Are you sure you want to close the application?")) {
            System.exit(0);
        }
    }

    /**
     * Signs user out of the program and reloads LoginScreenController
     *
     * @param event Sign out button action
     * @throws IOException if initialization error occurs
     */
    @FXML
    void onActionSignOutBtn(ActionEvent event) throws IOException {
        if (Alerts.confirmAlert("Confirm", "Are you sure you want to logout?")) {
            Stage stage = ((Stage)((Button)event.getSource()).getScene().getWindow());
            Parent scene = FXMLLoader.load(getClass().getResource("/view/LoginScreen.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    /**
     * Loads the AppointmentReportController
     *
     * @param event Appointment report button action
     * @throws IOException if initialization error occurs
     */
    @FXML
    void onActionCustomerReport(ActionEvent event) throws IOException {
        Stage stage = ((Stage)((Button)event.getSource()).getScene().getWindow());
        Parent scene = FXMLLoader.load(getClass().getResource("/view/AppointmentReport.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /**
     * Loads the ContactViewController
     *
     * @param event Contact view button action
     * @throws IOException if initialization error occurs
     */
    @FXML
    void onActionContactView(ActionEvent event) throws IOException {
        Stage stage = ((Stage)((Button)event.getSource()).getScene().getWindow());
        Parent scene = FXMLLoader.load(getClass().getResource("/view/ContactView.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /**
     * Loads the CustomerViewController
     *
     * @param event Customer view button action
     * @throws IOException if initialization error occurs
     */
    @FXML
    void onActionCustomerViewBtn(ActionEvent event) throws IOException {
        Stage stage = ((Stage)((Button)event.getSource()).getScene().getWindow());
        Parent scene = FXMLLoader.load(getClass().getResource("/view/CustomerView.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * @return Customer that is selected for modification
     */
    public static Customer getSelectedCustomer() {
        return selectedCustomer;
    }

    /**
     * @return Appointment that is selected for modification
     */
    public static Appointment getSelectedAppointment(){
        return selectedAppointment;
    }

    /**
     * Sets appointment table to show items per week
     *
     * @throws SQLException if SQL error from retrieval attempt occurs
     * @throws ParseException if time conversion error occurs
     */
    public void loadAptWeekTable() throws SQLException, ParseException {
        aptTable.setItems(getAppointmentsByWeek(datePicker.getValue()));
        aptIdColumn.setCellValueFactory(new PropertyValueFactory<>("aptId"));
        aptTitleColumn.setCellValueFactory(new PropertyValueFactory<>("aptTitle"));
        aptDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("aptDescription"));
        aptLocationColumn.setCellValueFactory(new PropertyValueFactory<>("aptLocation"));
        aptContactColumn.setCellValueFactory(new PropertyValueFactory<>("contactId"));
        aptTypeColumn.setCellValueFactory(new PropertyValueFactory<>("aptType"));
        aptStartColumn.setCellValueFactory(new PropertyValueFactory<>("aptStart"));
        aptEndColumn.setCellValueFactory(new PropertyValueFactory<>("aptEnd"));
        aptCustIdColumn.setCellValueFactory(new PropertyValueFactory<>("CustomerId"));
        aptUserIdColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));

    }

    /**
     * Sets appointment table to show items per month
     *
     * @throws SQLException if SQL error from retrieval attempt occurs
     * @throws ParseException if time conversion error occurs
     */
    public void loadAptMonthTable () throws SQLException, ParseException {
        aptTable.setItems(getAppointmentsByMonth(datePicker.getValue()));
        aptIdColumn.setCellValueFactory(new PropertyValueFactory<>("aptId"));
        aptTitleColumn.setCellValueFactory(new PropertyValueFactory<>("aptTitle"));
        aptDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("aptDescription"));
        aptLocationColumn.setCellValueFactory(new PropertyValueFactory<>("aptLocation"));
        aptContactColumn.setCellValueFactory(new PropertyValueFactory<>("contactId"));
        aptTypeColumn.setCellValueFactory(new PropertyValueFactory<>("aptType"));
        aptStartColumn.setCellValueFactory(new PropertyValueFactory<>("aptStart"));
        aptEndColumn.setCellValueFactory(new PropertyValueFactory<>("aptEnd"));
        aptCustIdColumn.setCellValueFactory(new PropertyValueFactory<>("CustomerId"));
        aptUserIdColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));

    }

    /**
     * Sets appointment table to show all items
     *
     * @throws SQLException if SQL error from retrieval attempt occurs
     * @throws ParseException if time conversion error occurs
     */
    public void loadAllAptTable () throws SQLException, ParseException {
        aptTable.setItems(getAllAppointments());
        aptIdColumn.setCellValueFactory(new PropertyValueFactory<>("aptId"));
        aptTitleColumn.setCellValueFactory(new PropertyValueFactory<>("aptTitle"));
        aptDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("aptDescription"));
        aptLocationColumn.setCellValueFactory(new PropertyValueFactory<>("aptLocation"));
        aptContactColumn.setCellValueFactory(new PropertyValueFactory<>("contactId"));
        aptTypeColumn.setCellValueFactory(new PropertyValueFactory<>("aptType"));
        aptStartColumn.setCellValueFactory(new PropertyValueFactory<>("aptStart"));
        aptEndColumn.setCellValueFactory(new PropertyValueFactory<>("aptEnd"));
        aptCustIdColumn.setCellValueFactory(new PropertyValueFactory<>("CustomerId"));
        aptUserIdColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));

    }

        public static void loadMainView(Stage stageInput) throws IOException
        {
            Stage stage = stageInput;
            Parent scene = FXMLLoader.load(MainViewController.class.getResource("/view/MainView.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }



    /**
     * Initializes controller; sets appointment and customer tables
     *
     * @param url Relative path for root object
     * @param resourceBundle Resources used to localize root object
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            userLabel.setText("User: " + getCurrentUser().getUserName());

            datePicker.setValue(LocalDate.now());

            aptTable.setItems(getAllAppointments());
            aptIdColumn.setCellValueFactory(new PropertyValueFactory<>("aptId"));
            aptTitleColumn.setCellValueFactory(new PropertyValueFactory<>("aptTitle"));
            aptDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("aptDescription"));
            aptLocationColumn.setCellValueFactory(new PropertyValueFactory<>("aptLocation"));
            aptContactColumn.setCellValueFactory(new PropertyValueFactory<>("contactId"));
            aptTypeColumn.setCellValueFactory(new PropertyValueFactory<>("aptType"));
            aptStartColumn.setCellValueFactory(new PropertyValueFactory<>("aptStart"));
            aptEndColumn.setCellValueFactory(new PropertyValueFactory<>("aptEnd"));
            aptCustIdColumn.setCellValueFactory(new PropertyValueFactory<>("CustomerId"));
            aptUserIdColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));

            custTable.setItems(getAllCustomers());
            custIDColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
            custNameColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
            custAddressColumn.setCellValueFactory(new PropertyValueFactory<>("customerAddress"));
            custPostalCodeColumn.setCellValueFactory(new PropertyValueFactory<>("customerPostalCode"));
            custPhoneColumn.setCellValueFactory(new PropertyValueFactory<>("customerPhoneNumber"));
            custDivisionColumn.setCellValueFactory(new PropertyValueFactory<>("customerDivisionId"));

        } catch (SQLException | ParseException e) {
            e.printStackTrace();
        }

    }
}