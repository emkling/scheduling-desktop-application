package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.AppointmentDAO;
import model.User;
import util.Alerts;
import util.GeneralMessage;

import java.io.*;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;

import static model.UserDAO.setCurrentUser;


/**
 * Controller class that dictates access to the application
 *
 */
public class LoginScreenController implements Initializable {


    @FXML
    private Button enterButton;

    @FXML
    private Button exitBtn;

    @FXML
    private Label loginLabel;

    @FXML
    private TextField passwordLogin;

    @FXML
    private TextField usernameLogin;

    @FXML
    private Label zoneLabel;

    private String errorTitle;

    private String blankAlertHeader;

    private String incorrectAlertHeader;

    private String confirmationAlertHeader;

    private String confirmationTitle;


    /**
     * Closes the program
     *
     * @param actionEvent Exit button action
     */
    @FXML
    public void onActionLoginExitBtn(ActionEvent actionEvent) {
        if (Alerts.confirmAlert(confirmationTitle, confirmationAlertHeader)) {
            System.exit(0);
        }
    }

    /**
     * Upon valid username and password, the MainViewController is loaded
     *
     * @param actionEvent Login button action
     * @throws IOException if initialization error occurs
     * @throws SQLException if SQL error occurs
     * @throws ParseException if parsing error occurs
     */
    @FXML
    public void onActionLoginBtn(ActionEvent actionEvent) throws IOException, SQLException, ParseException {

            String usernameCheck = usernameLogin.getText();
            String passwordCheck = passwordLogin.getText();

            boolean usernameSuccess = usernameCheck(usernameCheck);
            boolean passwordSuccess = passwordCheck(passwordCheck);

            if (usernameCheck.isEmpty() || passwordCheck.isEmpty()) {
                Alerts.errorAlert(errorTitle, blankAlertHeader);
                }

            else if (usernameSuccess && passwordSuccess) {
                if (usernameCheck.equals("test")) {
                    User currentUser = new User(1,"test", "test");
                    setCurrentUser(currentUser);
                    logger(usernameCheck, usernameSuccess);
                }

                else if (usernameCheck.equals("admin")) {
                    User currentUser = new User(2, "admin", "admin");
                    setCurrentUser(currentUser);
                }
                loadMainMenu(actionEvent);
                ;
            }

            else {
                Alerts.errorAlert(errorTitle, incorrectAlertHeader);
                logger(usernameCheck, false);
            }
    }

    /**
     * Returns validity determiner
     *
     * @param username Username to be checked
     * @return boolean if username is valid
     */
    public boolean usernameCheck(String username) {

        return username.equals("test");
    }

    /**
     * Returns validity determiner
     *
     * @param password Password to be checked
     * @return boolean if username is valid
     */
    public boolean passwordCheck(String password) {

        return password.equals("test");
    }

    /**
     * Loads MainviewController
     *
     * @param event ActionEvent event
     * @throws IOException if initialization error occurs
     * @throws SQLException if SQL error for retrieval attempt occurs
     * @throws ParseException if time conversion fails due to parsing errors
     */
    public void loadMainMenu (ActionEvent event) throws IOException, SQLException, ParseException {
        Stage stage = ((Stage)((Button)event.getSource()).getScene().getWindow());
        Parent scene = FXMLLoader.load(getClass().getResource("/view/MainView.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

        AppointmentDAO.upcomingAppointmentAlert();
    }

    /**
     * Logs application login attempts.
     *
     *
     * @param userName Username attempt to be recorded
     * @param success Determines whether login was successful
     * @throws IOException if runtime error occurs
     */
    public void logger(String userName, boolean success) throws IOException {
        String file = "login_activity.txt";

        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formatDateTime = dateTime.format(format);

        BufferedWriter buffer = new BufferedWriter(new FileWriter(file, true));
        PrintWriter output = new PrintWriter(buffer);

        GeneralMessage loginSuccess = (s,t) -> output.println("username attempt: "+ "'" + s + "'"+ " logged in successfully at " + formatDateTime);

        GeneralMessage loginFail = (s,t) -> output.println("username attempt: " + "'" +  s + "'" + " failed login at " + formatDateTime);

        if (success) {
            loginSuccess.outputMessage(userName,formatDateTime);
        }
        else {
            loginFail.outputMessage(userName,formatDateTime);
        }
        output.flush();

    }

    /**
     * Initializes controller
     *
     * @param url Relative path for root object
     * @param resourceB Resources used to localize root object
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceB) {

        resourceB = ResourceBundle.getBundle("resource/Languages", Locale.getDefault());

        loginLabel.setText(resourceB.getString("Login"));
        zoneLabel.setText(resourceB.getString("Zone"));
        exitBtn.setText(resourceB.getString("Exit"));
        enterButton.setText(resourceB.getString("Enter"));
        usernameLogin.setPromptText(resourceB.getString("Username"));
        passwordLogin.setPromptText(resourceB.getString("Password"));
        errorTitle = resourceB.getString("errorTitle");
        blankAlertHeader = resourceB.getString("blankAlertHeader");
        confirmationAlertHeader = resourceB.getString("confirmationAlertHeader");
        incorrectAlertHeader = resourceB.getString("incorrectAlertHeader");
        confirmationTitle = resourceB.getString("confirmationAlertTitle");
    }
}