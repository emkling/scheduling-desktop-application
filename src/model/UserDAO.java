package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import util.JDBC;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Data access object class for user objects
 */
public class UserDAO {

    /**
     * Returns current user
     */
    private static User currentUser = new User(0, null, null);

    /**
     * List of all users
     */
    private static ObservableList<User> allUsers = FXCollections.observableArrayList();

    /**
     * Returns list of all users
     *
     * @return list of users
     * @throws SQLException if SQL error occurs from retrieval attempt
     */
    private static ObservableList<User> getAllUsers () throws SQLException {
        Statement statement = JDBC.connection.createStatement();
        String queryInput = "Select * FROM USERS";
        ResultSet rs = statement.executeQuery(queryInput);
        while (rs.next()) {
            User user = new User(

                    rs.getInt("User_ID"),
                    rs.getString("User_Name"),
                    rs.getString("Password"));

            allUsers.add(user);
        }
        return allUsers;
    }

    /**
     * Sets the current user value
     * @param user to be designated as the current user
     */
    public static void setCurrentUser (User user) {
        currentUser = user;
    }

    public static User getCurrentUser() {
        return currentUser;
    }


    /**
     * Retrieves a user from their ID
     *
     * @param id of user
     * @return user
     * @throws SQLException if SQL error occurs from retrieval attempt
     */
    public static User getUser(int id) throws SQLException {
        Statement statement = JDBC.connection.createStatement();
        String queryInput= "Select * FROM CONTACTS WHERE CONTACT_ID = " + id;
        ResultSet rs = statement.executeQuery((queryInput));
        User user = null;

        if(rs.next()) {
            user = new User(
                    rs.getInt("User_ID"),
                    rs.getString("User_Name"),
                    rs.getString("Password"));
        }
        return user;
    }

    /**
     * Get list of user IDs
     *
     * @return list of IDs
     * @throws SQLException if SQL error occurs from retrieval attempt
     */
    public static ObservableList<Integer> getUserIdList () throws SQLException {
        ObservableList<Integer> userIdList = FXCollections.observableArrayList();
        Statement statement = JDBC.connection.createStatement();
        String queryInput = "Select * FROM users";
        ResultSet rs = statement.executeQuery((queryInput));

        while (rs.next()) {
           userIdList.add(rs.getInt("User_ID"));
        }
        return userIdList;
    }

}
