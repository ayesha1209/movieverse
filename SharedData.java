package application;

import java.time.LocalDate;

public class SharedData {
    private static LocalDate selectedDate;

    public static LocalDate getSelectedDate() {
        return selectedDate;
    }

    public static void setSelectedDate(LocalDate date) {
        selectedDate = date;
    }
}

