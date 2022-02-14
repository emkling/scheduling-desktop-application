package util;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

/**
 * Class that creates alerts
 */
public class Alerts {

    /**
     * Creates an error alert
     *
     * @param title of error alert
     * @param header of error alert
     */
    public static void errorAlert (String title, String header) {
        Alert error = new Alert(Alert.AlertType.ERROR);
        error.setTitle(title);
        error.setHeaderText(header);
        error.showAndWait();
    }

    /**
     * Creates an information alert
     *
     * @param title of information alert
     * @param header of information alert
     */
    public static void informationAlert (String title, String header) {
        Alert confirm = new Alert(Alert.AlertType.INFORMATION);
        confirm.setTitle(title);
        confirm.setHeaderText(header);
        Optional<ButtonType> userChoice = confirm.showAndWait();
    }

    /**
     * Creates an information alert with a body option
     * @param title of information alert
     * @param header of information alert
     * @param body of information alert
     */
    public static void informationAlert (String title, String header, String body) {
        Alert confirm = new Alert(Alert.AlertType.INFORMATION);
        confirm.setTitle(title);
        confirm.setHeaderText(header);
        confirm.setContentText(body);
        Optional<ButtonType> userChoice = confirm.showAndWait();
    }

    /**
     * Creates a confirmation alert
     *
     * @param title of confirmation alert
     * @param header of confirmation alert
     */
    public static boolean confirmAlert(String title, String header) {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle(title);
        confirm.setHeaderText(header);
        Optional<ButtonType> userChoice = confirm.showAndWait();

        if (userChoice.get() == ButtonType.OK) {
         return true;
        }
        else {
         return false;
         }
    }
}
