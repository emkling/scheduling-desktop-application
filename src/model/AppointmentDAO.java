package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import util.Alerts;
import util.JDBC;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.util.Date;


/**
 * Data access object class for appointments
 *
 *
 */
public class AppointmentDAO {

    /**
     * Stores all appointments
     */
    public static ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();

    /**
     * Returns list of appointments
     *
     * @return list of all appointment objects
     * @throws SQLException   if SQL error occurs from retrieval attempt
     * @throws ParseException if time conversion error occurs
     */
    public static ObservableList<Appointment> getAllAppointments() throws SQLException, ParseException {
        allAppointments.clear();

        Statement statement = JDBC.connection.createStatement();
        String queryInput = "Select * FROM APPOINTMENTS";
        ResultSet rs = statement.executeQuery(queryInput);

        while (rs.next()) {
            Appointment appointment = new Appointment(
                    rs.getInt("Appointment_ID"),
                    rs.getString("Title"),
                    rs.getString("Description"),
                    rs.getString("Location"),
                    rs.getString("Type"),
                    dateTimeInputLoader(utcTimeToLocal(dateTimeOutputLoader(rs.getString("Start")))),
                    dateTimeInputLoader(utcTimeToLocal(dateTimeOutputLoader(rs.getString("End")))),
                    rs.getInt("Customer_ID"),
                    rs.getInt("User_ID"),
                    rs.getInt("Contact_ID"));
            allAppointments.add(appointment);
        }
        return allAppointments;
    }

    /**
     * Finds and returns an appointment based on its ID number
     *
     * @param id number of appointment
     * @return appointment with matching id
     * @throws SQLException if SQL error occurs from retrieval attempt
     */
    public static Appointment getAppointmentById(int id) throws SQLException {
        Statement statement = JDBC.connection.createStatement();
        Appointment appointment = null;
        String queryInput = "SELECT * FROM APPOINTMENTS WHERE Appointment_ID = " + id + "";
        ResultSet rs = statement.executeQuery((queryInput));

        if (rs.next()) {
            appointment = new Appointment(
                    rs.getInt("Appointment_ID"),
                    rs.getString("Title"),
                    rs.getString("Description"),
                    rs.getString("Location"),
                    rs.getString("Type"),
                    rs.getString("Start"),
                    rs.getString("End"),
                    rs.getInt("Customer_ID"),
                    rs.getInt("User_ID"),
                    rs.getInt("Contact_ID")
            );
        }
        return appointment;
    }


    /**
     * Adds a new appointment to the database
     *
     * @param appointment to be added to the database
     * @param user        the person that makes the addition
     * @throws SQLException if SQL error occurs from insertion attempt
     */
    public static void insertAppointmentRow(Appointment appointment, String user) throws SQLException {
        Statement statement = JDBC.connection.createStatement();
        String queryInput = "INSERT INTO APPOINTMENTS" +
                " VALUES ("
                + appointment.getAptId() + ", "
                + "'" + appointment.getAptTitle() + "', "
                + "'" + appointment.getAptDescription() + "', "
                + "'" + appointment.getAptLocation() + "', "
                + "'" + appointment.getAptType() + "', "
                + "'" + appointment.getAptStart() + "', "
                + "'" + appointment.getAptEnd() + "', "
                + "NOW(), "
                + "'" + user + "', "
                + "NOW(), "
                + "'" + user + "', "
                + appointment.getCustomerId() + ", "
                + appointment.getUserId() + ", "
                + appointment.getContactId() + ")";
        statement.execute(queryInput);
    }

    /**
     * Updates an appointment that already exists within the database
     *
     * @param appt to be added to the database
     * @param user that makes the addition
     * @param id of appointment to be updated
     * @throws SQLException if SQL error occurs from update attempt
     */
    public static void updateAppointmentRow(Appointment appt, String user, int id) throws SQLException {
        Statement statement = JDBC.connection.createStatement();
        String queryInput = "UPDATE APPOINTMENTS"
                + " Set Appointment_ID = " + id
                + ", Title = " + "'" + appt.getAptTitle() + "'"
                + ", Description = " + "'" + appt.getAptDescription() + "'"
                + ", Location = " + "'" + appt.getAptLocation() + "'"
                + ", Type = " + "'" + appt.getAptType() + "'"
                + ", Start = " + "'" + appt.getAptStart() + "'"
                + ", End = " + "'" + appt.getAptEnd() + "'"
                + ", Create_Date = NOW(), "
                + "Created_By = " + "'" + user + "', "
                + "Last_Update = NOW(), "
                + "Last_Updated_By = " + "'" + user + "', "
                + "Customer_ID = " + appt.getCustomerId() + ", "
                + "User_ID = " + appt.getUserId() + ", "
                + "Contact_ID = " + appt.getContactId() + " "
                + "WHERE APPOINTMENT_ID = " + id;
        statement.execute(queryInput);
    }

    /**
     * Retrieves appointments that correspond to a contact ID
     *
     * @param contactId refers to contact
     * @return list of appointments
     * @throws SQLException   if SQL error occurs from retrieval attempt
     * @throws ParseException if time conversion fails
     */
    public static ObservableList<Appointment> getAppointmentsByContactId(int contactId) throws SQLException, ParseException {
        Statement statement = JDBC.connection.createStatement();
        ObservableList<Appointment> contactAppointments = FXCollections.observableArrayList();
        String queryInput = "SELECT * FROM APPOINTMENTS WHERE Contact_ID = " + contactId + "";
        ResultSet rs = statement.executeQuery((queryInput));

        Appointment appointment = null;

        while (rs.next()) {
            appointment = new Appointment(
                    rs.getInt("Appointment_ID"),
                    rs.getString("Title"),
                    rs.getString("Description"),
                    rs.getString("Location"),
                    rs.getString("Type"),
                    AppointmentDAO.dateTimeInputLoader(utcTimeToLocal(dateTimeOutputLoader(rs.getString("Start")))),
                    AppointmentDAO.dateTimeInputLoader(utcTimeToLocal(dateTimeOutputLoader(rs.getString("End")))),
                    rs.getInt("Customer_ID"),
                    rs.getInt("User_ID"),
                    rs.getInt("Contact_ID")
            );
            contactAppointments.add(appointment);
        }
        return contactAppointments;
    }

    /**
     * Retrieves appointments that correspond to a customer ID
     *
     * @param customerId refers to customer
     * @return list of appointments
     * @throws SQLException   if SQL error occurs from retrieval attempt
     * @throws ParseException if time conversion fails
     */
    public static ObservableList<Appointment> getAppointmentsByCustomerId(int customerId) throws SQLException, ParseException {
        Statement statement = JDBC.connection.createStatement();
        ObservableList<Appointment> contactAppointments = FXCollections.observableArrayList();
        String queryInput = "SELECT * FROM APPOINTMENTS WHERE Customer_ID = " + customerId + "";
        ResultSet rs = statement.executeQuery((queryInput));

        Appointment appointment = null;

        while (rs.next()) {
            appointment = new Appointment(
                    rs.getInt("Appointment_ID"),
                    rs.getString("Title"),
                    rs.getString("Description"),
                    rs.getString("Location"),
                    rs.getString("Type"),
                    dateTimeInputLoader(utcTimeToLocal(dateTimeOutputLoader(rs.getString("Start")))),
                    dateTimeInputLoader(utcTimeToLocal(dateTimeOutputLoader(rs.getString("End")))),
                    rs.getInt("Customer_ID"),
                    rs.getInt("User_ID"),
                    rs.getInt("Contact_ID")
            );
            contactAppointments.add(appointment);
        }
        return contactAppointments;
    }

    /**
     * Retrieves all the types of appointments and places them in a String list
     *
     * @return list of types
     * @throws SQLException   if SQL error occurs from retrieval attempt
     * @throws ParseException if time conversion fails
     */
    public static ObservableList<String> getAppointmentTypeList() throws SQLException, ParseException {
        Statement statement = JDBC.connection.createStatement();
        ObservableList<String> aptTypeList = FXCollections.observableArrayList();
        String queryInput = "SELECT * FROM APPOINTMENTS";
        ResultSet rs = statement.executeQuery((queryInput));

        aptTypeList.add("None");
        aptTypeList.add("All");


        while (rs.next()) {

            aptTypeList.add(rs.getString("Type"));
        }
        return aptTypeList;
    }

    /**
     * Retrieves all the years of appointments and places them in a String list
     *
     * @return list of years
     * @throws SQLException   if SQL error occurs from retrieval attempt
     * @throws ParseException if time conversion fails
     */
    public static ObservableList<String> getAppointmentYearList() throws SQLException, ParseException {
        Statement statement = JDBC.connection.createStatement();
        ObservableList<String> aptYearList = FXCollections.observableArrayList();
        String queryInput = "SELECT * FROM APPOINTMENTS GROUP BY EXTRACT(year FROM start)";
        ResultSet rs = statement.executeQuery((queryInput));

        aptYearList.add("None");
        aptYearList.add("All");


        Appointment appointment = null;

        while (rs.next()) {

            aptYearList.add(rs.getString("Start").substring(0, 4));
        }
        return aptYearList;
    }

    /**
     * Returns a list of appointments that match a particular month
     *
     * @param month that user wishes to view
     * @return list of appointments that align with the selected month
     * @throws SQLException   if SQL error occurs from retrieval attempt
     * @throws ParseException if time conversion fails
     */
    public static ObservableList<Appointment> getAppointmentsByMonth(String month) throws SQLException, ParseException {
        Statement statement = JDBC.connection.createStatement();
        ObservableList<Appointment> aptTypeList = FXCollections.observableArrayList();
        int monthNum = 0;

        if (month.equals("January")) {
            monthNum = 1;
        } else if (month.equals("February")) {
            monthNum = 2;
        } else if (month.equals("March")) {
            monthNum = 3;
        } else if (month.equals("April")) {
            monthNum = 4;
        } else if (month.equals("May")) {
            monthNum = 5;
        } else if (month.equals("June")) {
            monthNum = 6;
        } else if (month.equals("July")) {
            monthNum = 7;
        } else if (month.equals("August")) {
            monthNum = 8;
        } else if (month.equals("September")) {
            monthNum = 9;
        } else if (month.equals("October")) {
            monthNum = 10;
        } else if (month.equals("November")) {
            monthNum = 11;
        } else {
            monthNum = 12;
        }

        String queryInput = "SELECT * FROM APPOINTMENTS WHERE EXTRACT(month FROM start) = " + monthNum;
        String queryInput2 = "SELECT * FROM APPOINTMENTS";
        ResultSet rs;

        if (month.equals("All")) {
            rs = statement.executeQuery((queryInput2));
        } else if (month.equals("None")) {
            return aptTypeList;
        } else {
            rs = statement.executeQuery((queryInput));
        }
        Appointment appointment = null;

        while (rs.next()) {
            appointment = new Appointment(
                    rs.getInt("Appointment_ID"),
                    rs.getString("Title"),
                    rs.getString("Description"),
                    rs.getString("Location"),
                    rs.getString("Type"),
                    AppointmentDAO.dateTimeInputLoader(utcTimeToLocal(dateTimeOutputLoader(rs.getString("Start")))),
                    AppointmentDAO.dateTimeInputLoader(utcTimeToLocal(dateTimeOutputLoader(rs.getString("End")))),
                    rs.getInt("Customer_ID"),
                    rs.getInt("User_ID"),
                    rs.getInt("Contact_ID")
            );
            aptTypeList.add(appointment);
        }
        return aptTypeList;
    }

    /**
     * Retrieves a list of appointments that are scheduled within a particular year
     *
     * @param year user wishes to view
     * @return list of appointments
     * @throws SQLException   if SQL error occurs from retrieval attempt
     * @throws ParseException if time conversion fails
     */
    public static ObservableList<Appointment> getAppointmentsByYear(String year) throws SQLException, ParseException {
        Statement statement = JDBC.connection.createStatement();
        ObservableList<Appointment> aptTypeList = FXCollections.observableArrayList();
        String queryInput = "SELECT * FROM APPOINTMENTS WHERE EXTRACT(year FROM start) = " + "'" + year + "'";
        String queryInput2 = "SELECT * FROM APPOINTMENTS";
        ResultSet rs;

        if (year.equals("All")) {
            rs = statement.executeQuery((queryInput2));
        } else if (year.equals("None")) {
            return aptTypeList;
        } else {
            rs = statement.executeQuery((queryInput));
        }
        Appointment appointment = null;

        while (rs.next()) {
            appointment = new Appointment(
                    rs.getInt("Appointment_ID"),
                    rs.getString("Title"),
                    rs.getString("Description"),
                    rs.getString("Location"),
                    rs.getString("Type"),
                    AppointmentDAO.dateTimeInputLoader(AppointmentDAO.utcTimeToLocal(AppointmentDAO.dateTimeOutputLoader(rs.getString("Start")))),
                    AppointmentDAO.dateTimeInputLoader(AppointmentDAO.utcTimeToLocal(AppointmentDAO.dateTimeOutputLoader(rs.getString("End")))),
                    rs.getInt("Customer_ID"),
                    rs.getInt("User_ID"),
                    rs.getInt("Contact_ID")
            );
            aptTypeList.add(appointment);
        }
        return aptTypeList;
    }

    /**
     * Retrieves a list of appointments that match a specific type
     *
     * @param type of appointment that is selected
     * @return list of appointments
     * @throws SQLException   if SQL error occurs from retrieval attempt
     * @throws ParseException if time conversion fails
     */
    public static ObservableList<Appointment> getAppointmentsByType(String type) throws SQLException, ParseException {
        Statement statement = JDBC.connection.createStatement();
        ObservableList<Appointment> aptTypeList = FXCollections.observableArrayList();
        String queryInput = "SELECT * FROM APPOINTMENTS WHERE Type = " + "'" + type + "'";
        String queryInput2 = "SELECT * FROM APPOINTMENTS";
        ResultSet rs;

        if (type.equals("All")) {
            rs = statement.executeQuery((queryInput2));
        } else if (type.equals("None")) {
            return aptTypeList;
        } else {
            rs = statement.executeQuery((queryInput));
        }

        Appointment appointment = null;

        while (rs.next()) {
            appointment = new Appointment(
                    rs.getInt("Appointment_ID"),
                    rs.getString("Title"),
                    rs.getString("Description"),
                    rs.getString("Location"),
                    rs.getString("Type"),
                    AppointmentDAO.dateTimeInputLoader(AppointmentDAO.utcTimeToLocal(AppointmentDAO.dateTimeOutputLoader(rs.getString("Start")))),
                    AppointmentDAO.dateTimeInputLoader(AppointmentDAO.utcTimeToLocal(AppointmentDAO.dateTimeOutputLoader(rs.getString("End")))),
                    rs.getInt("Customer_ID"),
                    rs.getInt("User_ID"),
                    rs.getInt("Contact_ID")
            );
            aptTypeList.add(appointment);
        }
        return aptTypeList;
    }

    /**
     * Deletes an appointment from the database that has a matching ID
     *
     * @param id of appointment
     * @throws SQLException if SQL error occurs from deletion attempt
     */
    public static void deleteAppointment(int id) throws SQLException {
        Statement statement = JDBC.connection.createStatement();
        String queryInput = "DELETE FROM APPOINTMENTS WHERE APPOINTMENT_ID = " + id;
        statement.execute(queryInput);
    }

    /**
     * Generates unique IDs for new appointments
     *
     * @return new id for appointment
     * @throws SQLException   if SQL error occurs from retrieval attempt
     * @throws ParseException if error occurs processing times
     */
    public static int aptIdGenerator() throws SQLException, ParseException {
        int newId = getAllAppointments().size() + 1;
        boolean success = false;

        ObservableList<Integer> check = FXCollections.observableArrayList();
        Statement statement = JDBC.connection.createStatement();

        String queryInput = "SELECT * FROM Appointments WHERE Appointment_ID =  " + newId;
        ResultSet rs = statement.executeQuery((queryInput));

        while (rs.next()) {
            check.add(rs.getInt("Appointment_ID"));
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

    /**
    * Checks if the appointment is within operating hours of the business
    *
    * @param t1 start time
    * @param t2 end time
    * @return boolean indicating whether time is free or taken
    */
    public static boolean checkValidApptTime(String t1, String t2) {
            boolean valid = false;
            LocalDateTime startTime = dateTimeUtility(t1);
            LocalDateTime endTime = dateTimeUtility(t2);
            ZonedDateTime zone = startTime.atZone(ZoneId.of(ZoneId.systemDefault().toString()));
            ZonedDateTime zone2 = endTime.atZone(ZoneId.of(ZoneId.systemDefault().toString()));
            ZonedDateTime estZone = zone.withZoneSameInstant(ZoneId.of("America/New_York"));
            ZonedDateTime estZone2 = zone2.withZoneSameInstant(ZoneId.of("America/New_York"));
            LocalTime convertedStartTime = estZone.toLocalDateTime().toLocalTime();
            LocalTime convertedEndTime = estZone2.toLocalDateTime().toLocalTime();

            LocalTime openTime = LocalTime.parse("08:00");
            LocalTime closeTime = LocalTime.parse("22:00");

                if ((convertedStartTime.isAfter(openTime) && convertedStartTime.isBefore(closeTime))
                        && (convertedEndTime.isAfter(openTime) && convertedEndTime.isBefore(closeTime))) {
                    valid = true;
                }

                return valid;
        }

        /**
         * Checks for overlapping appointment conflicts
         *
         * @param t1 is the start time
         * @param t2 is the end time
         * @return boolean indicating whether appointment can be scheduled
         * @throws SQLException if SQL error occurs from retrieval attempt
         */
        public static boolean checkTimeConflicts(String t1, String t2) throws SQLException {
            boolean valid = true;
            ObservableList<Integer> count = FXCollections.observableArrayList();
            LocalDateTime time1 = dateTimeUtility(t1);
            LocalDateTime time2 = dateTimeUtility(t2);

            String utcStartTime = localTimeToUTC(time1);
            String utcEndTime = localTimeToUTC(time2);
            Statement statement = JDBC.connection.createStatement();

                String queryInput = "SELECT * FROM APPOINTMENTS WHERE "
                        + "(('" + utcStartTime + "'" + "BETWEEN START AND END) OR "
                        + "('" + utcEndTime + "'" + "BETWEEN START AND END) OR "
                        + "(end BETWEEN " + "'" + utcStartTime + "'" + " AND " + "'" + utcEndTime + "'" + ") OR"
                        + "(start BETWEEN " + "'" + utcStartTime + "'" + " AND " + "'" + utcEndTime + "'" + "))";

                ResultSet rs = statement.executeQuery((queryInput));
                while (rs.next()) {
                    count.add(rs.getInt("Appointment_ID"));

                }
                if (count.size() > 0) {
                    valid = false;
                }
                return valid;
        }

    /**
     * Checks for overlapping appointment conflicts and excludes appointments with a matching ID
     *
     * @param t1 is the start time
     * @param t2 is the end time
     * @param id is the appointment the user is updating
     * @return boolean indicating whether appointment can be scheduled
     * @throws SQLException if SQL error occurs from retrieval attempt
     */
    public static boolean checkTimeConflicts(String t1, String t2, int id) throws SQLException {
        boolean valid = true;
        ObservableList<Integer> count = FXCollections.observableArrayList();
        LocalDateTime time1 = dateTimeUtility(t1);
        LocalDateTime time2 = dateTimeUtility(t2);

        String utcStartTime = localTimeToUTC(time1);
        String utcEndTime = localTimeToUTC(time2);
        Statement statement = JDBC.connection.createStatement();

        String queryInput = "SELECT * FROM APPOINTMENTS WHERE "
                + "(('" + utcStartTime + "'" + "BETWEEN START AND END) OR "
                + "('" + utcEndTime + "'" + "BETWEEN START AND END) OR "
                + "(end BETWEEN " + "'" + utcStartTime + "'" + " AND " + "'" + utcEndTime + "'" + ") OR"
                + "(start BETWEEN " + "'" + utcStartTime + "'" + " AND " + "'" + utcEndTime + "'" + "))";

        ResultSet rs = statement.executeQuery((queryInput));
        while (rs.next()) {
            count.add(rs.getInt("Appointment_ID"));

        }
        if (count.size() > 0) {
            if(count.contains(id) && count.size()==1) {
                valid = true;
            }
            else {
                valid = false;
            }
        }
        return valid;
    }

    /**
     * Ensures that the appointment is scheduled during the week day
     *
     * @param d1 start day
     * @param d2 end day
     * @return boolean indicating whether requested appointment day is valid
     */
    public static boolean checkValidApptDay(LocalDate d1, LocalDate d2) {
            boolean valid = false;
            DayOfWeek day1 = DayOfWeek.of(d1.get(ChronoField.DAY_OF_WEEK));
            DayOfWeek day2 = DayOfWeek.of(d2.get(ChronoField.DAY_OF_WEEK));

            if ((day1 != DayOfWeek.SUNDAY && day1 != DayOfWeek.SATURDAY) && (day2 != DayOfWeek.SUNDAY && day2 != DayOfWeek.SATURDAY) ) {
                valid = true;
            }
            return valid;
        }

    /**
     * Retrieves appointments within a month of the selected date
     *
     * @param ld selection date to be referenced
     * @return list of appointments within the next month
     * @throws SQLException if SQL error occurs from retrieval attempt
     * @throws ParseException if time conversion error occurs
     */
    public static ObservableList<Appointment> getAppointmentsByMonth(LocalDate ld) throws SQLException, ParseException {
            ObservableList<Appointment> weekAppointments = FXCollections.observableArrayList();
            Statement statement = JDBC.connection.createStatement();
            String queryInput = "SELECT * FROM Appointments WHERE Start Between " + "'" + ld + "'" + " and Date_ADD( " + "'"  + ld + "'" + ", INTERVAL 1 Month)";
            ResultSet rs = statement.executeQuery((queryInput));

            while(rs.next()) {
            Appointment appointment = new Appointment(
                    rs.getInt("Appointment_ID"),
                    rs.getString("Title"),
                    rs.getString("Description"),
                    rs.getString("Location"),
                    rs.getString("Type"),
                    dateTimeInputLoader(utcTimeToLocal(dateTimeOutputLoader(rs.getString("Start")))),
                    dateTimeInputLoader(utcTimeToLocal(dateTimeOutputLoader(rs.getString("End")))),
                    rs.getInt("Customer_ID"),
                    rs.getInt("User_ID"),
                    rs.getInt("Contact_ID"));
            weekAppointments.add(appointment);
        }
        return weekAppointments;
    }

    /**
     * Retrieves appointments within a week of the selected date
     *
     * @param ld selection date to be referenced
     * @return list of appointments within the next week
     * @throws SQLException if SQL error occurs from retrieval attempt
     * @throws ParseException if time conversion error occurs
     */
        public static ObservableList<Appointment> getAppointmentsByWeek(LocalDate ld) throws SQLException, ParseException {
            ObservableList<Appointment> weekAppointments = FXCollections.observableArrayList();
            Statement statement = JDBC.connection.createStatement();
            String queryInput = "SELECT * FROM Appointments WHERE Start Between " + "'" + ld + "'" + " and Date_ADD( " + "'"  + ld + "'" + ", INTERVAL 7 day)";
            ResultSet rs = statement.executeQuery((queryInput));

            while(rs.next()) {
                Appointment appointment = new Appointment(
                        rs.getInt("Appointment_ID"),
                        rs.getString("Title"),
                        rs.getString("Description"),
                        rs.getString("Location"),
                        rs.getString("Type"),
                        dateTimeInputLoader(utcTimeToLocal(dateTimeOutputLoader(rs.getString("Start")))),
                        dateTimeInputLoader(utcTimeToLocal(dateTimeOutputLoader(rs.getString("End")))),
                        rs.getInt("Customer_ID"),
                        rs.getInt("User_ID"),
                        rs.getInt("Contact_ID"));
                weekAppointments.add(appointment);
            }
            return weekAppointments;
        }


    /**
     * Checks to see if an appointment is within the next 15 minutes
     *
     * @throws SQLException if SQL error occurs from retrieval attempt
     * @throws ParseException if time conversion error occurs
     */
    public static void upcomingAppointmentAlert() throws SQLException, ParseException {
        ObservableList<Appointment> upcomingApt = FXCollections.observableArrayList();
            LocalDateTime dateTime = LocalDateTime.now();
            DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formatDateTime = dateTime.format(format);
            LocalDateTime finalTime = LocalDateTime.parse(formatDateTime, format);
            String finalOutput = localTimeToUTC(finalTime);

            Statement statement = JDBC.connection.createStatement();
            String queryInput = "SELECT * FROM Appointments WHERE Start Between " + "'" + finalOutput + "'" + " and Date_ADD( " + "'"  + finalOutput + "'" + ", INTERVAL 15 MINUTE)";
            ResultSet rs = statement.executeQuery((queryInput));
            while (rs.next()) {
                Appointment appointment = new Appointment(
                        rs.getInt("Appointment_ID"),
                        rs.getString("Title"),
                        rs.getString("Description"),
                        rs.getString("Location"),
                        rs.getString("Type"),
                        rs.getString("Start"),
                        rs.getString("End"),
                        rs.getInt("Customer_ID"),
                        rs.getInt("User_ID"),
                        rs.getInt("Contact_ID"));
                upcomingApt.add(appointment);
            }

            if (upcomingApt.size() > 0) {
                for (Appointment app : upcomingApt)
                Alerts.informationAlert("Upcoming Appointment", "You have an upcoming appointment: ", " Appointment ID: "
                        + app.getAptId() + "   Type: " + app.getAptType() + "  Time: " + dateTimeInputLoader(utcTimeToLocal(dateTimeOutputLoader(app.getAptStart()))).substring(11));
            }
            else {
                Alerts.informationAlert("Appointment Notice", "No upcoming appointments");
            }

        }

    /**
     * Converts string timestamps that have AM/PM into LocalDateTimes
     *
     * @param date selected for conversion
     * @return LocalDateTime value
     */
    public static LocalDateTime dateTimeUtility(String date) {
            DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-d hh:mm:ss a");
            LocalDateTime newFormat = LocalDateTime.parse(date, format);

            return newFormat;
        }

    /**
     * Converts string timestamps that do not have AM/PM signifiers into LocalDateTimes
     *
     * @param date selected for conversion
     * @return LocalDateTime value
     */
    public static LocalDateTime dateTimeOutputLoader(String date) {
            DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-d HH:mm:ss");
            LocalDateTime newFormat = LocalDateTime.parse(date, format);

            return newFormat;
        }

    /**
     * Converts string timestamps from 24 hour time into 12 hour time with AM/PM signifiers
     *
     * @param date timestamp to be converted
     * @return String with AM/PM signifier
     * @throws ParseException if time conversion error occurs
     */
        public static String dateTimeInputLoader(String date) throws ParseException {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            DateFormat output = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss aa");
            Date newDate = df.parse(date);
            String finalOutput = output.format(newDate);

            return finalOutput;
        }

    /**
     * Converts LocalDateTime of the user's local time into a string of its equivalent UTC time
     *
     * @param dt is the value for conversion
     * @return string of time in UTC
     */
    public static String localTimeToUTC (LocalDateTime dt) {
            ZonedDateTime zone = dt.atZone(ZoneId.of(ZoneId.systemDefault().toString()));
            ZonedDateTime utcZone = zone.withZoneSameInstant(ZoneId.of("UTC"));
            LocalDateTime convertedTime = utcZone.toLocalDateTime();
            String utcTime = convertedTime.toString().replace("T"," ");

            return utcTime;
        }

    /**
     * Converts LocalDateTime of UTC time in the database and converts it to a string that represents the user's local time
     *
     * @param dt is the value for conversion
     * @return string signifying user's local time
     */
    public static String utcTimeToLocal (LocalDateTime dt) {
            ZonedDateTime zdtOut = dt.atZone(ZoneId.of("UTC"));
            ZonedDateTime zdtToLocal = zdtOut.withZoneSameInstant(ZoneId.of(ZoneId.systemDefault().toString()));
            LocalDateTime ldtFinal = zdtToLocal.toLocalDateTime();
            String localTime = ldtFinal.toString().replace("T"," ");

            return localTime;
        }

    /**
     * Converts a LocalDateTime value into a LocalDate value
     *
     * @param dt LocalDateTime desired to be converted
     * @return date only
     */
    public static LocalDate utcTimeToLocalDate (LocalDateTime dt) {
            ZonedDateTime zone = dt.atZone(ZoneId.of("UTC"));
            ZonedDateTime localZone = zone.withZoneSameInstant(ZoneId.of(ZoneId.systemDefault().toString()));
            LocalDateTime convertedTime = localZone.toLocalDateTime();
            LocalDate date = convertedTime.toLocalDate();

            return date;
        }
}