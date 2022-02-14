package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Class for user objects
 */
public class User {

    private final IntegerProperty userId;

    private final StringProperty userName;

    private final StringProperty password;

    /**
     * Constructor for user objects
     *
     * @param userId of user
     * @param userName of user
     * @param password of user
     */
    public User (int userId, String userName, String password) {
        this.userId = new SimpleIntegerProperty(userId);
        this.userName = new SimpleStringProperty(userName);
        this.password = new SimpleStringProperty(password);
    }

    //Getters
    public int getUserId() { return userId.get(); }

    public String getUserName() { return userName.get(); }

    public String getPassword() { return password.get(); }

    //Setters
    public void setUserName(String userName) { this.userName.set(userName); }

    public void setPassword(String password) { this.password.set(password); }

    public void setUserId(int userId) { this.userId.set(userId); }

    //Property getters
    public IntegerProperty userIdProperty() { return userId; }

    public StringProperty userNameProperty() { return userName; }

    public StringProperty passwordProperty() { return password; }


}
