package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import util.JDBC;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static model.DivisionDAO.getDivisionCountry;

/**
 * Database access object class used for customers
 *
 */
public class CustomerDAO {

    /**
     * List of allCustomers
     */
    public static final ObservableList<Customer> allCustomers = FXCollections.observableArrayList();


    /**
     * Retrieves a list of all customers
     *
     * @return list of customers
     * @throws SQLException if SQL error occurs from retrieval attempt
     */
    public static ObservableList<Customer> getAllCustomers() throws SQLException {
            allCustomers.clear();
            Statement statement = JDBC.connection.createStatement();
            String queryInput = "Select * FROM CUSTOMERS";
            ResultSet rs = statement.executeQuery(queryInput);

            while (rs.next()) {
                Customer customer = new Customer(
                        rs.getInt("Customer_ID"),
                        rs.getString("Customer_Name"),
                        rs.getString("Address"),
                        rs.getString("Postal_Code"),
                        rs.getString("Phone"),
                        rs.getInt("Division_ID"));
                allCustomers.add(customer);
            }
            return allCustomers;
        }

    /**
     * Returns a customer based on ID
     *
     * @param id of customer
     * @return Customer
     * @throws SQLException if SQL error occurs from retrieval attempt
     */
    public static Customer getCustomer(int id) throws SQLException {
            Statement statement = JDBC.connection.createStatement();
            String queryInput = "SELECT * FROM Customers WHERE Customer_ID = " + id + "";
            ResultSet rs = statement.executeQuery(queryInput);

            Customer customerFound = null;
            if (rs.next()) {
                customerFound = new Customer(
                        rs.getInt("Customer_ID"),
                        rs.getString("Customer_Name"),
                        rs.getString("Address"),
                        rs.getString("Postal_Code"),
                        rs.getString("Phone"),
                        rs.getInt("Division_ID"));
                statement.close();

            }
            return customerFound;
        }

    /**
     * Adds a new customer to the database
     *
     * @param customer desired to be added
     * @param user making the additions
     * @throws SQLException if SQL error occurs from insertion attempt
     */
    public static void insertCustomerRow(Customer customer, String user) throws SQLException {
            Statement statement = JDBC.connection.createStatement();
            customer.setCustomerAddress(getDivisionCountry(customer.getCustomerDivisionId(),customer.getCustomerAddress()));
            String queryInput = "INSERT INTO CUSTOMERS"
                    + " VALUES ("
                    + customer.getCustomerId() + ", "
                    + "'" + customer.getCustomerName() + "', "
                    + "'" + customer.getCustomerAddress() + "', "
                    + "'" + customer.getCustomerPostalCode() + "', "
                    + "'" + customer.getCustomerPhoneNumber() + "',"
                    + "NOW(), "
                    + "'" + user + "', "
                    + "NOW(),"
                    + "'" + user + "', "
                    + customer.getCustomerDivisionId()
                    + ");";

            statement.execute(queryInput);
        }

    /**
     * Updates an existing customer with desired changes
     *
     * @param customer desired to be updated
     * @param user making the changes
     * @param id of the customer that is to be updated
     * @throws SQLException if SQL error occurs from update attempt
     */
    public static void updateCustomerRow(Customer customer, String user, int id) throws SQLException {
            Statement statement = JDBC.connection.createStatement();
            String queryInput = "UPDATE CUSTOMERS"
                    + " Set Customer_ID = " + id + ", "
                    + "Customer_Name = " + "'" + customer.getCustomerName() + "', "
                    + "Address = " + "'" + customer.getCustomerAddress() + "', "
                    + "Postal_Code = " + "'" + customer.getCustomerPostalCode() + "', "
                    + "Phone = " + "'" + customer.getCustomerPhoneNumber() + "', "
                    + "Create_Date = NOW(), "
                    + "Created_By = " + "'" + user + "', "
                    + "Last_Update = NOW(), "
                    + "Last_Updated_By = " + "'" + user + "', "
                    + "Division_ID = " + customer.getCustomerDivisionId() + " "
                    + "WHERE Customer_ID = " + id + ";";

            statement.execute(queryInput);
        }

    /**
     * Deletes a customer with a matching ID
     *
     * @param id of customer to be deleted
     * @throws SQLException if SQL error occurs from deletion attempt
     */
    public static void deleteCustomerRow(int id) throws SQLException {
            Statement statement = JDBC.connection.createStatement();
            String queryInput = "DELETE FROM CUSTOMERS WHERE CUSTOMER_ID = " + id;
            statement.execute(queryInput);
            }

    /**
     * Returns a list of all customer IDs
     *
     * @return list of IDs
     * @throws SQLException if SQL error occurs from retrieval attempt
     */
    public static ObservableList<Integer> getCustomerIdList () throws SQLException {
            ObservableList<Integer> customerIdList = FXCollections.observableArrayList();
            Statement statement = JDBC.connection.createStatement();
            String queryInput = "Select * FROM customers";
            ResultSet rs = statement.executeQuery((queryInput));

            while (rs.next()) {
                customerIdList.add(rs.getInt("Customer_ID"));
            }
            return customerIdList;
        }


    /**
     * Returns a customer ID from their name
     *
     * @param name of customer
     * @return ID of customer
     * @throws SQLException if SQL retrieval error occurs
     */
    public static int getCustomerIdFromName(String name) throws SQLException {
            Statement statement = JDBC.connection.createStatement();
            String queryInput = "SELECT * FROM Customers WHERE Customer_Name = " +"'" + name + "'";
            ResultSet rs = statement.executeQuery(queryInput);
            int id = 0;

            if (rs.next()) {
                     id = rs.getInt("Customer_ID");

            }
            return id;
        }

    /**
     * Returns a list of all customer names
     *
     * @return list of names
     * @throws SQLException if SQL error occurs from retrieval attempt
     */
    public static ObservableList<String> getCustomerNameList () throws SQLException {
            ObservableList<String> customerNameList = FXCollections.observableArrayList();
            Statement statement = JDBC.connection.createStatement();
            String queryInput = "Select * FROM customers";
            ResultSet rs = statement.executeQuery((queryInput));

            while (rs.next()) {
                customerNameList.add(rs.getString("Customer_Name"));
            }
            return customerNameList;
        }

    /**
     * Generates a unique ID for new customer additions
     *
     * @return ID
     * @throws SQLException if SQL error occurs from retrieval attempt
     */
    public static int customerIdGenerator() throws SQLException {
            int newId = getAllCustomers().size() + 1;
            boolean valid = false;

            ObservableList<Integer> check = FXCollections.observableArrayList();
            Statement statement = JDBC.connection.createStatement();

            String queryInput = "SELECT * FROM Customers WHERE Customer_ID =  " + newId;
            ResultSet rs = statement.executeQuery((queryInput));

            while (rs.next()) {
                check.add(rs.getInt("Customer_ID"));
            }
            if (check.size() > 0) {
                if (check.contains(newId)) {
                    newId++;
                    if (check.contains(newId)) {
                        newId++;
                    }
                }
            }
            return newId;
        }
    }

