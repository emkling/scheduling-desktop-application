package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import util.JDBC;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Data access object class for countries
 *
 */
public class CountryDAO {

    /**
     * List of all countries in the database
     */
    private static ObservableList<Country> allCountries = FXCollections.observableArrayList();

    /**
     * Returns list of all the countries in the database
     *
     * @return list of countries
     * @throws SQLException if SQL error occurs from retrieval attempt
     */
    public static ObservableList<Country> getAllCountries() throws SQLException {
        Statement statement = JDBC.connection.createStatement();
        String queryInput = "Select * FROM COUNTRIES";
        ResultSet rs = statement.executeQuery(queryInput);
        while (rs.next()) {
            Country country = new Country(

                    rs.getInt("Country_ID"),
                    rs.getString("Country"));

            allCountries.add(country);
        }
        return allCountries;
    }

    /**
     * Returns country by ID
     *
     * @param id of country
     * @return country
     * @throws SQLException if SQL error occurs from retrieval attempt
     */
    public static Country getCountryByID(int id) throws SQLException {
        Statement statement = JDBC.connection.createStatement();
        String queryInput = "Select * FROM CONTACTS WHERE CONTACT_ID = " + id;
        ResultSet rs = statement.executeQuery((queryInput));
        Country country = null;

        if (rs.next()) {
            country = new Country(
                    rs.getInt("Country_ID"),
                    rs.getString("Country"));
        }
        return country;
    }

    /**
     * Returns list of country names
     *
     * @return list of countries
     * @throws SQLException if SQL error occurs from retrieval attempt
     */
    public static ObservableList<String> getCountryNameList() throws SQLException {
        ObservableList<String> countryList = FXCollections.observableArrayList();
        Statement statement = JDBC.connection.createStatement();
        String queryInput = "Select * FROM countries";
        ResultSet rs = statement.executeQuery((queryInput));

        while (rs.next()) {
            countryList.add(rs.getString("Country"));
        }
        return countryList;

    }
}
