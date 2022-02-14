package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Division {
    private final IntegerProperty divisionId;

    private final StringProperty divisionName;

    private final IntegerProperty divisionCountry;

    public Division (int id, String name, int country) {
        divisionId = new SimpleIntegerProperty(id);
        divisionName = new SimpleStringProperty(name);
        divisionCountry = new SimpleIntegerProperty(country);

    }

    //Getters
    public int getDivisionId() { return divisionId.get(); }

    public String getDivisionName() { return divisionName.get(); }

    public int getDivisionCountry() { return divisionCountry.get(); }


    //Setters
    public void setDivisionName(String divisionName) { this.divisionName.set(divisionName); }

    public void setDivisionId(int divisionId) { this.divisionId.set(divisionId); }

    public void setDivisionCountry(int divisionCountry) { this.divisionCountry.set(divisionCountry); }

    //Property getters
    public StringProperty divisionNameProperty() { return divisionName; }

    public IntegerProperty divisionIdProperty() { return divisionId; }

    public IntegerProperty divisionCountryProperty() { return divisionCountry; }

}
