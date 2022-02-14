package main;

import util.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Starting point of application that connects
 * to the database, and launches the GUI
 */
public class Main extends Application {


    /**
     * Starting class for the application
     *
     * @param stage Stage
     * @throws Exception if error starting stage or scene occurs
     */
    @Override
    public void start(Stage stage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("/view/LoginScreen.fxml"));
        stage.setTitle("Login");
        stage.setScene(new Scene(root, 462, 400));
        stage.show();
    }

    /**
     * Starting point of application that connects to the database, and launches the GUI;
     * @param args String[] args
     */
    public static void main(String[] args) {
        JDBC.openConnection();
        launch(args);
        JDBC.closeConnection();
    }
}