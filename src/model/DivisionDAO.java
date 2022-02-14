package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import util.JDBC;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Data access object class for divisions
 */
public class DivisionDAO {

    /**
     * List of all divisions
     */
    private static ObservableList<Division> allDivisions = FXCollections.observableArrayList();

    /**
     * Returns a list of all divisions
     *
     * @return list of divisions
     * @throws SQLException if SQL error occurs from retrieval attempt
     */
    public static ObservableList<Division> getAllDivisions() throws SQLException {
        Statement statement = JDBC.connection.createStatement();
        String queryInput = "Select * FROM first_level_divisions";
        ResultSet rs = statement.executeQuery(queryInput);

        while(rs.next()) {
            Division division = new Division(

                    rs.getInt("Division_ID"),
                    rs.getString("Division"),
                    rs.getInt("Country_ID"));

            allDivisions.add(division);
        }
        return allDivisions;
    }

    /**
     * Returns the division ID from its state parameter
     *
     * @param state of division
     * @return ID of division
     * @throws SQLException if SQL error occurs from S
     */
    public static int getDivisionIdFromState(String state) throws SQLException {
        Statement statement = JDBC.connection.createStatement();
        int selection = 0;
        String queryInput= "Select * FROM first_level_divisions WHERE Division = " + "'" + state + "'";
        ResultSet rs = statement.executeQuery((queryInput));

        if(rs.next()){
            selection = rs.getInt("Division_ID");
        }
        return selection;
    }

    /**
     * Returns a division based on its ID
     *
     * @param id of division
     * @return division
     * @throws SQLException if SQL error occurs from retreival attempt
     */
    public static Division getDivisionFromID (int id) throws SQLException {
        Statement statement = JDBC.connection.createStatement();
        String queryInput= "Select * FROM first_level_divisions WHERE CONTACT_ID = " + id;
        ResultSet rs = statement.executeQuery((queryInput));
        Division division = null;

        if(rs.next()) {
            division = new Division(
                    rs.getInt("Division_ID"),
                    rs.getString("Division"),
                    rs.getInt("Country_ID"));
        }
        return division;
    }

    /**
     * Returns the state of a division based on its ID
     *
     * @param id of division
     * @return state
     * @throws SQLException if SQL error occurs from retrieval attempt
     */
    public static String getStateFromDivisionID (int id) throws SQLException {
        Statement statement = JDBC.connection.createStatement();
        String state = null;
        String queryInput= "Select * FROM first_level_divisions WHERE Division_ID = " + id;
        ResultSet rs = statement.executeQuery((queryInput));

        if(rs.next()){
            state = rs.getString("Division");
        }
        return state;
    }

    /**
     * Returns list of states by country ID
     *
     * @param countryId of division
     * @return list of states
     * @throws SQLException if SQL error occurs from retrieval attempt
     */
    public static ObservableList<String> getStateList(int countryId) throws SQLException {
        Statement statement = JDBC.connection.createStatement();
        ObservableList<String> stateList = FXCollections.observableArrayList();
        String queryInput= "Select * FROM first_level_divisions WHERE COUNTRY_ID = " + countryId;
        ResultSet rs = statement.executeQuery((queryInput));

        while(rs.next()) {
            stateList.add(rs.getString("Division"));
        }
        return stateList;
    }

    /**
     * Returns newly formatted address that account for its country
     *
     * @param id of division
     * @param addy of customer
     * @return string of address that includes country
     * @throws SQLException if SQL error occurs from retrieval attempt
     */
    public static String getDivisionCountry(int id, String addy) throws SQLException {
        Statement statement = JDBC.connection.createStatement();
        String queryInput = "Select Country_ID FROM first_level_divisions WHERE Division_ID = " + id;
        ResultSet rs = statement.executeQuery((queryInput));
        int countryId = 0;
        String formattedAddress = null;

        if (rs.next()) {
            countryId = rs.getInt("Country_ID");
        }

        if (countryId == 1 ) {
            formattedAddress = "U.S. Address: " + addy;
        }
        else if (countryId == 3) {
            formattedAddress = "Canadian Address: " + addy;
        }
        else if (countryId == 2) {
            formattedAddress = "UK Address: " + addy;
        }
        else if (countryId == 0)
        {
            formattedAddress = addy;
        }

        return formattedAddress;
    }

    /**
     * Returns country ID based on division ID
     *
     * @param id of division
     * @return country ID
     * @throws SQLException if SQL error occurs from retrieval attempt
     */
    public static int getDivisionCountry(int id) throws SQLException {
        Statement statement = JDBC.connection.createStatement();
        String queryInput = "Select Country_ID FROM first_level_divisions WHERE Division_ID = " + id;
        ResultSet rs = statement.executeQuery((queryInput));
        int countryId = 0;
        if (rs.next()) {
            countryId = rs.getInt("Country_ID");
        }
        return countryId;
    }

}
