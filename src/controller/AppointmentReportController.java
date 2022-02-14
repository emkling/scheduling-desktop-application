package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;
import util.GeneralInterface;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ResourceBundle;

import static controller.MainViewController.loadMainView;
import static model.AppointmentDAO.*;

/**
 * Controller class that shows appointments by type, month, and year
 *
 */
public class AppointmentReportController implements Initializable {

    @FXML
    private TableColumn<Appointment, Integer> aptIdColumn;

    @FXML
    private TableView<Appointment> aptTable;

    @FXML
    private TableColumn<Appointment, Integer> contactIdColumn;

    @FXML
    private ComboBox<String> monthComboBox;

    @FXML
    private TableColumn<Appointment, String> descriptionColumn;

    @FXML
    private TableColumn<Appointment, String> endColumn;

    @FXML
    private TableColumn<Appointment, String> startColumn;

    @FXML
    private TableColumn<Appointment, String> titleColumn;

    @FXML
    private Label totalAptLabel;

    @FXML
    private TableColumn<Appointment, String> typeColumn;

    @FXML
    private ComboBox<String> typeComboBox;

    @FXML
    private ComboBox<String> yearComboBox;


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
     * Sets the table to show appointments by month.
     *
     * @param event Month combo box action
     * @throws SQLException if SQL error occurs from retrieval attempt
     * @throws ParseException if parse error occurs from time conversion attempt
     */
    @FXML
    void onActionMonthComboBox(ActionEvent event) throws SQLException, ParseException {
        aptTable.setItems(getAppointmentsByMonth(monthComboBox.getValue()));
        aptIdColumn.setCellValueFactory(new PropertyValueFactory<>("aptId"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("aptTitle"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("aptDescription"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("aptType"));
        startColumn.setCellValueFactory(new PropertyValueFactory<>("aptStart"));
        endColumn.setCellValueFactory(new PropertyValueFactory<>("aptEnd"));
        contactIdColumn.setCellValueFactory(new PropertyValueFactory<>("CustomerId"));

        //Lambda expression
        GeneralInterface aptCount = n -> n.size();
        totalAptLabel.setText("Total Appointments Found: " + aptCount.calculateSize(getAppointmentsByMonth(monthComboBox.getValue())));

        yearComboBox.setValue("None");
        typeComboBox.setValue("None");
    }

    /**
     * Sets the table to show appointments by year.
     *
     * A lambda expression was used for simplicity and functionality purposes.
     * The expression calculates the count of items in the tableview.
     *
     * @param event Year combo box action
     * @throws SQLException if SQL retrieval error occurs
     * @throws ParseException if time parsing error occurs
     */
    @FXML
    void onActionYearComboBox(ActionEvent event) throws SQLException, ParseException {
        aptTable.setItems(getAppointmentsByYear(yearComboBox.getValue()));
        aptIdColumn.setCellValueFactory(new PropertyValueFactory<>("aptId"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("aptTitle"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("aptDescription"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("aptType"));
        startColumn.setCellValueFactory(new PropertyValueFactory<>("aptStart"));
        endColumn.setCellValueFactory(new PropertyValueFactory<>("aptEnd"));
        contactIdColumn.setCellValueFactory(new PropertyValueFactory<>("CustomerId"));

        //Lambda expression
        GeneralInterface aptCount = n -> n.size();
        totalAptLabel.setText("Total Appointments Found: " + aptCount.calculateSize(getAppointmentsByMonth(yearComboBox.getValue())));

       typeComboBox.setValue("None");
       monthComboBox.setValue("None");
    }

    /**
     * Sets the table to show appointments by type.
     *
     * A lambda expression was used for simplicity and functionality purposes.
     * The expression calculates the count of items in the tableview.
     *
     * @param event Combo box action
     * @throws SQLException if SQL error occurs from retrieval attempt
     * @throws ParseException if parse error occurs from time conversion attempt
     */
    @FXML
    void onActionTypeComboBox(ActionEvent event) throws SQLException, ParseException {

        aptTable.setItems(getAppointmentsByType(typeComboBox.getValue()));
        aptIdColumn.setCellValueFactory(new PropertyValueFactory<>("aptId"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("aptTitle"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("aptDescription"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("aptType"));
        startColumn.setCellValueFactory(new PropertyValueFactory<>("aptStart"));
        endColumn.setCellValueFactory(new PropertyValueFactory<>("aptEnd"));
        contactIdColumn.setCellValueFactory(new PropertyValueFactory<>("CustomerId"));

        //Lambda expression
        GeneralInterface aptCount = n -> n.size();
        totalAptLabel.setText("Total Appointments Found: " + aptCount.calculateSize(getAppointmentsByMonth(typeComboBox.getValue())));

        monthComboBox.setValue("None");
        yearComboBox.setValue("None");
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
            ObservableList<String> months = FXCollections.observableArrayList("None","All", "January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December");
            typeComboBox.setValue("None");
            monthComboBox.setValue("None");
            yearComboBox.setValue("None");

            typeComboBox.setItems(getAppointmentTypeList());
            monthComboBox.setItems(months);
            yearComboBox.setItems(getAppointmentYearList());

        } catch (SQLException | ParseException e) {
            e.printStackTrace();
        }
    }
}