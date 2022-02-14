package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.sql.SQLException;

/**
 * Class for customer objects
 */
public class Customer {

    private final IntegerProperty customerId;

    private final StringProperty customerName;

    private final StringProperty customerAddress;

    private final StringProperty customerPostalCode;

    private final StringProperty customerPhoneNumber;

    private final IntegerProperty customerDivisionId;


    /**
     * Constructor for customer objects
     *
     * @param id of customer
     * @param name of customer
     * @param address of customer
     * @param postalCode of customer
     * @param phoneNumber of customer
     * @param divisionId of customer
     * @throws SQLException if SQL error occurs from retrieval attempt
     */
    public Customer (int id, String name, String address, String postalCode, String phoneNumber, int divisionId) throws SQLException {

        customerId = new SimpleIntegerProperty(id);
        customerName = new SimpleStringProperty(name);
        customerAddress = new SimpleStringProperty(address);
        customerPostalCode = new SimpleStringProperty(postalCode);
        customerPhoneNumber = new SimpleStringProperty(phoneNumber);
        customerDivisionId = new SimpleIntegerProperty(divisionId);
    }

    //Getters
    public int getCustomerId() { return customerId.get(); }

    public String getCustomerName() { return customerName.get(); }

    public String getCustomerAddress() { return customerAddress.get(); }

    public String getCustomerPostalCode() { return customerPostalCode.get(); }

    public String getCustomerPhoneNumber() { return customerPhoneNumber.get(); }

    public int getCustomerDivisionId() { return customerDivisionId.get(); }

    //Setters
    public void setCustomerId(int customerId) { this.customerId.set(customerId); }

    public void setCustomerAddress(String customerAddress) { this.customerAddress.set(customerAddress); }

    public void setCustomerPostalCode(String customerPostalCode) { this.customerPostalCode.set(customerPostalCode); }

    public void setCustomerPhoneNumber(String customerPhoneNumber) { this.customerPhoneNumber.set(customerPhoneNumber); }

    public void setCustomerDivisionId(int customerDivisionId) { this.customerDivisionId.set(customerDivisionId); }

    public void setCustomerName(String customerName) { this.customerName.set(customerName); }

    //Property getters
    public IntegerProperty customerIdProperty() { return customerId; }

    public StringProperty customerNameProperty() { return customerName; }

    public StringProperty customerAddressProperty() { return customerAddress; }

    public StringProperty customerPostalCodeProperty() { return customerPostalCode; }

    public StringProperty customerPhoneNumberProperty() { return customerPhoneNumber; }

    public IntegerProperty customerDivisionIdProperty() { return customerDivisionId; }


}
