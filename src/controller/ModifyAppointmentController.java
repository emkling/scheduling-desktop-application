package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.*;
import util.Alerts;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

import static controller.MainViewController.getSelectedAppointment;
import static controller.MainViewController.loadMainView;
import static model.AppointmentDAO.*;
import static model.ContactDAO.getContactIdFromName;
import static model.UserDAO.getCurrentUser;


/**
 * Controller class that allows for the update of appointments in the database
 *
 */
public class ModifyAppointmentController implements Initializable {

    @FXML
    private TextField aptIdTextField;

    @FXML
    private ComboBox<String> contactIDComboBox;

    @FXML
    private ComboBox<Integer> customerIDComboBox;

    @FXML
    private TextField descriptionTextField;

    @FXML
    private DatePicker endDateSelector;

    @FXML
    private TextField endTimeTextField;

    @FXML
    private TextField locationTextField;

    @FXML
    private DatePicker startDateSelector;

    @FXML
    private TextField startTimeTextField;

    @FXML
    private TextField titleTextField;

    @FXML
    private TextField typeTextField;

    @FXML
    private ComboBox<Integer> userIDComboBox;


    /**
     * Reloads MainViewController
     * @param event Cancel button action
     * @throws IOException if initialization error occurs
     */
    @FXML
    void onActionCancelBtn(ActionEvent event) throws IOException {
        if (Alerts.confirmAlert("Confirm", "Are you sure you want to cancel?")) {

            Stage stage = ((Stage)((Button)event.getSource()).getScene().getWindow());
            loadMainView(stage);
        }
    }

    /**
     * Updates row in database that aligns with Appointment ID
     * @param event Save button action
     */
    @FXML
    void onActionSaveBtn(ActionEvent event) {
        try {
            String title = titleTextField.getText();
            String description = descriptionTextField.getText();
            String location = locationTextField.getText();
            String type = typeTextField.getText();
            String startDate = String.valueOf(startDateSelector.getValue());
            LocalDate startCheck = startDateSelector.getValue();
            String startTime = startTimeTextField.getText().toUpperCase();
            String endDate = String.valueOf(endDateSelector.getValue());
            LocalDate endCheck = endDateSelector.getValue();
            String endTime = endTimeTextField.getText().toUpperCase();
            String contactName = contactIDComboBox.getValue();
            Integer customerId = customerIDComboBox.getValue();
            Integer userId = userIDComboBox.getValue();

            if (title.isEmpty() || description.isEmpty() || location.isEmpty() || type.isEmpty() || startDateSelector.getValue() == null || startTime.isEmpty()
                    || endDateSelector.getValue() == null || endTime.isEmpty()) {
                Alerts.errorAlert("Error", "Fields left blank");
            } else {
                if (startTime.length() != 11 || endTime.length() != 11 || (!startTime.contains("AM") && !startTime.contains("PM") || (!endTime.contains("AM") && !endTime.contains("PM"))) || !startTime.contains(" ") || !endTime.contains(" ")) {
                    Alerts.errorAlert("Error", "Please ensure start and end times are correctly formatted (HH:DD:SS PM)");
                }
                if (userId == null || customerId == null || contactName.isEmpty()) {
                    Alerts.errorAlert("Error", "Please select combo box options");
                } else {
                    String start = startDate + " " + startTime;
                    String end = endDate + " " + endTime;
                    if (!AppointmentDAO.checkValidApptTime(start, end)) {
                        Alerts.errorAlert("Error", "Please ensure appointment times are between the hours of 8:00 AM to 10:00 PM EST");
                    }
                    else {
                        if (!AppointmentDAO.checkValidApptDay(startCheck, endCheck)) {
                            Alerts.errorAlert("Error", "Please ensure appointment dates are scheduled for weekdays only");
                        }
                        else {
                                if (!AppointmentDAO.checkTimeConflicts(start, end, getSelectedAppointment().getAptId())) {
                                    Alerts.errorAlert("Error", "There was a time conflict with your requested appointment. Please choose a new time");
                                }
                                else {
                                LocalDateTime finalStart = dateTimeUtility(start);
                                LocalDateTime finalEnd = dateTimeUtility(end);

                                Appointment appointment = new Appointment(getSelectedAppointment().getAptId(), title, description, location, type,
                                        localTimeToUTC(finalStart), localTimeToUTC(finalEnd), customerId, userId, getContactIdFromName(contactName));

                                Alerts.informationAlert("Success", "Appointment was added to the database");
                                updateAppointmentRow(appointment, getCurrentUser().getUserName(), getSelectedAppointment().getAptId());


                                Stage stage = ((Stage) ((Button) event.getSource()).getScene().getWindow());
                                loadMainView(stage);
                                }
                        }
                    }
                }
            }
        }
        catch (Exception e) {
            Alerts.errorAlert("Error", "Failed to add appointment");
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
        aptIdTextField.setText(String.valueOf(getSelectedAppointment().getAptId()));
        titleTextField.setText(getSelectedAppointment().getAptTitle());
        descriptionTextField.setText(getSelectedAppointment().getAptDescription());
        locationTextField.setText(getSelectedAppointment().getAptLocation());
        typeTextField.setText(getSelectedAppointment().getAptType());

        try {
            contactIDComboBox.setItems(ContactDAO.getContactList());
            customerIDComboBox.setItems(CustomerDAO.getCustomerIdList());
            userIDComboBox.setItems((UserDAO.getUserIdList()));
            contactIDComboBox.setValue(ContactDAO.getContactFromID(getSelectedAppointment().getContactId()).getContactName());
            customerIDComboBox.setValue(getSelectedAppointment().getCustomerId());
            userIDComboBox.setValue(getSelectedAppointment().getUserId());
            startTimeTextField.setText(getSelectedAppointment().getAptStart().substring(11));
            endTimeTextField.setText(getSelectedAppointment().getAptEnd().substring(11));

            endDateSelector.setValue(utcTimeToLocalDate(dateTimeUtility(getSelectedAppointment().getAptStart())));
            startDateSelector.setValue(utcTimeToLocalDate(dateTimeUtility(getSelectedAppointment().getAptEnd())));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}