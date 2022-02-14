package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Class for appointment objects
 *
 */
public class Appointment {

    private final IntegerProperty aptId;

    private final StringProperty aptTitle;

    private final StringProperty aptDescription;

    private final StringProperty aptLocation;

    private final StringProperty aptType;

    private final StringProperty aptStart;

    private final StringProperty aptEnd;

    private final IntegerProperty customerId;

    private final IntegerProperty userId;

    private final IntegerProperty contactId;

    /**
     * Constructor for appointment objects
     *
     * @param aId Appointment ID
     * @param title of appointment
     * @param description of appointment
     * @param location of appointment
     * @param type of appointment
     * @param start time of appointment
     * @param end time of appointment
     * @param custId customer ID of appointment
     * @param uId user ID of appointment
     * @param contId contact ID of appointment
     */
    public Appointment(int aId, String title, String description, String location, String type, String start, String end, int custId, int uId, int contId)
    {
        aptId = new SimpleIntegerProperty(aId);
        aptTitle = new SimpleStringProperty(title);
        aptDescription = new SimpleStringProperty(description);
        aptLocation = new SimpleStringProperty(location);
        aptType = new SimpleStringProperty(type);
        aptStart = new SimpleStringProperty(start);
        aptEnd = new SimpleStringProperty(end);
        customerId = new SimpleIntegerProperty(custId);
        userId = new SimpleIntegerProperty(uId);
        contactId = new SimpleIntegerProperty(contId);
    }

    //Getters
    public int getAptId() {
        return aptId.get();
    }

    public String getAptTitle() { return aptTitle.get(); }

    public String getAptDescription() { return aptDescription.get(); }

    public String getAptLocation() { return aptLocation.get(); }

    public String getAptType() { return aptType.get(); }

    public String getAptEnd() { return aptEnd.get(); }

    public int getCustomerId() { return customerId.get(); }

    public int getUserId() { return userId.get(); }

    public String getAptStart() {

        return aptStart.get(); }

    public int getContactId() { return contactId.get(); }

    //Setters
    public void setAptId(int aptId) { this.aptId.set(aptId); }

    public void setApTitle(String aptTitle) { this.aptTitle.set(aptTitle); }

    public void setAptType(String aptType) { this.aptType.set(aptType); }

    public void setAptDescription(String aptDescription) { this.aptDescription.set(aptDescription); }

    public void setAptLocation(String aptLocation) { this.aptLocation.set(aptLocation); }

    public void setAptStart(String aptStart) { this.aptStart.set(aptStart); }

    public void setAptEnd(String aptEnd) { this.aptEnd.set(aptEnd); }

    public void setCustomerId(int customerId) { this.customerId.set(customerId); }

    public void setUserId(int userId) { this.userId.set(userId); }

    public void setContactId(int contactId) { this.contactId.set(contactId); }


    //Property getters
    public StringProperty aptLocationProperty() { return aptLocation; }

    public StringProperty aptDescriptionProperty() { return aptDescription; }

    public StringProperty aptTypeProperty() { return aptType; }

    public IntegerProperty aptIdProperty() { return aptId; }

    public StringProperty aptTitleProperty() { return aptTitle; }

    public StringProperty aptStartProperty() { return aptStart; }

    public StringProperty aptEndProperty() { return aptEnd; }

    public IntegerProperty customerIdProperty() { return customerId; }

    public IntegerProperty userIdProperty() { return userId; }

    public IntegerProperty contactIdProperty() { return contactId; }





}
