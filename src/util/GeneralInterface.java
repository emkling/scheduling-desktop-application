package util;

import javafx.collections.ObservableList;
import model.Appointment;

/**
 * Interface for size calculation
 */
public interface GeneralInterface {
    int calculateSize(ObservableList<Appointment> n);
}
