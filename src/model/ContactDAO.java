package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import util.JDBC;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Data access object class for contacts
 *
 */
public class ContactDAO {

    /**
     * Stores all contacts
     */
    public static ObservableList<Contact> allContacts = FXCollections.observableArrayList();

    /**
     * Returns list of all contacts
     *
     * @return List of all contacts
     * @throws SQLException if SQL error occurs from retrieval attempt
     */
    public static ObservableList<Contact> getAllContacts() throws SQLException {
        Statement statement = JDBC.connection.createStatement();
        String queryInput = "Select * FROM CONTACTS";
        ResultSet rs = statement.executeQuery(queryInput);

        while(rs.next()) {
            Contact contact = new Contact(

            rs.getInt("Contact_ID"),
            rs.getString("Contact_Name"),
            rs.getString("Email"));

            allContacts.add(contact);
        }
        return allContacts;
    }

    /**
     * Returns contact from id
     *
     * @param id of customer
     * @return Customer object
     * @throws SQLException if SQL error occurs from retrieval attempt
     */
    public static Contact getContactFromID (int id) throws SQLException {
        Statement statement = JDBC.connection.createStatement();
        String queryInput= "Select * FROM CONTACTS WHERE CONTACT_ID = " + id;
        ResultSet rs = statement.executeQuery((queryInput));
        Contact contact = null;

        if(rs.next()) {
             contact = new Contact(
                    rs.getInt("Contact_ID"),
                    rs.getString("Contact_Name"),
                    rs.getString("Email"));
        }
        return contact;
    }

    /**
     * Returns a list of the names of all contacts
     *
     * @return list of all contact names
     * @throws SQLException if SQL error occurs from retrieval attempt
     */
    public static ObservableList<String> getContactList () throws SQLException {
        ObservableList<String> contactList = FXCollections.observableArrayList();
        Statement statement = JDBC.connection.createStatement();
        String queryInput = "Select * FROM contacts";
        ResultSet rs = statement.executeQuery((queryInput));

        while (rs.next()) {
            contactList.add(rs.getString("Contact_Name"));
        }
        return contactList;
    }

    /**
     * Returns contact from name
     *
     * @param name of contact
     * @return id of contact
     * @throws SQLException if SQL error occurs from retrieval attempt
     */
    public static int getContactIdFromName (String name) throws SQLException {
        Statement statement = JDBC.connection.createStatement();
        String queryInput= "Select * FROM CONTACTS WHERE Contact_Name = " + "'"+ name + "'";
        ResultSet rs = statement.executeQuery((queryInput));
        int id = 0;

        if(rs.next()) {
                    id = rs.getInt("Contact_ID");

        }
        return id;
    }
}
