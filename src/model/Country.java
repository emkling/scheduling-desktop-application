package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


/**
 * Class for country objects
 */
public class Country {

    private final IntegerProperty countryId;

    private final StringProperty countryName;

    /**
     * Constructor for country objects
     *
     * @param id of country
     * @param name of country
     */
    public Country (int id, String name) {
        countryId = new SimpleIntegerProperty(id);
        countryName = new SimpleStringProperty(name);

    }

    //Getters
    public int getCountryId() { return countryId.get(); }

    public String getCountryName() { return countryName.get(); }

    //Setters
    public void setCountryId(int countryId) { this.countryId.set(countryId); }

    public void setCountryName(String countryName) { this.countryName.set(countryName); }

    //Property Getters
    public StringProperty countryNameProperty() { return countryName; }

    public IntegerProperty countryIdProperty() { return countryId; }
}
