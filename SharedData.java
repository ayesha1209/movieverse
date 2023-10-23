package application;

import java.time.LocalDate;

public class SharedData {
    private static LocalDate selectedDate;
    private static String selectedLocation;

    public static LocalDate getSelectedDate() {
        return selectedDate;
    }

    public static void setSelectedDate(LocalDate date) {
        selectedDate = date;
    }
    public static String getSelectedLocation() {
        return selectedLocation;
    }

    public static void setSelectedLocation(String location) {
        selectedLocation = location;
    }
}

