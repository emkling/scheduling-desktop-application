package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ResourceBundle;

import static controller.MainViewController.loadMainView;
import static model.AppointmentDAO.getAppointmentsByCustomerId;
import static model.CustomerDAO.getCustomerIdFromName;
import static model.CustomerDAO.getCustomerNameList;


/**
 * Controller that shows appointments for each customer
 *
 */
public class CustomerViewController implements Initializable {

    @FXML
    private TableColumn<Appointment, Integer> aptIdColumn;

    @FXML
    private TableView<Appointment> aptTable;

    @FXML
    private ComboBox<String> customerComboBox;

    @FXML
    private TableColumn<Appointment, Integer> contactIdColumn;

    @FXML
    private TableColumn<Appointment, String> descriptionColumn;

    @FXML
    private TableColumn<Appointment, String> endColumn;

    @FXML
    private TableColumn<Appointment, String> startColumn;

    @FXML
    private TableColumn<Appointment, String> titleColumn;

    @FXML
    private TableColumn<Appointment, String> typeColumn;

    /**
     * Exits controller and reloads MainViewController
     *
     * @param event Back button action
     * @throws IOException if initialization error occurs
     */
    @FXML
    void onActionBackBtn(ActionEvent event) throws IOException {
        Stage stage = ((Stage)((Button)event.getSource()).getScene().getWindow());
        loadMainView(stage);
    }

    /**
     * Sets the table to show appointments based on the selected customer
     *
     * @param event Contact combo box action
     * @throws SQLException if SQL error occurs from retrieval attempt
     * @throws ParseException if parse error occurs from time conversion attempt
     */
    @FXML
    void onActionComboBox(ActionEvent event) throws SQLException, ParseException {
        aptTable.setItems(getAppointmentsByCustomerId(getCustomerIdFromName(customerComboBox.getValue())));
        aptIdColumn.setCellValueFactory(new PropertyValueFactory<>("aptId"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("aptTitle"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("aptDescription"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("aptType"));
        startColumn.setCellValueFactory(new PropertyValueFactory<>("aptStart"));
        endColumn.setCellValueFactory(new PropertyValueFactory<>("aptEnd"));
        contactIdColumn.setCellValueFactory(new PropertyValueFactory<>("CustomerId"));
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
            customerComboBox.setItems(getCustomerNameList());
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}