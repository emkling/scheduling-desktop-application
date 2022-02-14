package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Class for contact objects
 */
public class Contact {
    private final IntegerProperty contactId;

    private final StringProperty contactName;

    private final StringProperty contactEmail;


    /**
     * Constructor class for contacts
     *
     * @param id Contact id
     * @param name Contact name
     * @param email Contact email
     */
    public Contact (int id, String name, String email) {
        contactId = new SimpleIntegerProperty(id);
        contactName = new SimpleStringProperty(name);
        contactEmail = new SimpleStringProperty(email);
    }

    // Getters
    public String getContactName() { return contactName.get(); }

    public String getContactEmail() { return contactEmail.get(); }

    public int getContactId() { return contactId.get(); }


    // Setters
    public void setContactName(String contactName) { this.contactName.set(contactName); }

    public void setContactEmail(String contactEmail) { this.contactEmail.set(contactEmail); }

    public void setContactId(int contactId) { this.contactId.set(contactId); }


    // Property getters
    public IntegerProperty contactIdProperty() { return contactId; }

    public StringProperty contactEmailProperty() { return contactEmail; }

    public StringProperty contactNameProperty() { return contactName; }

}
