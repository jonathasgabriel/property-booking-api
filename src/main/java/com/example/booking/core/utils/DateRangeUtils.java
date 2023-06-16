package com.example.booking.core.utils;

import java.time.LocalDate;

public class DateRangeUtils {

    public static boolean isDateRangeValid(LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null) {
            return false;
        }

        if (endDate.compareTo(startDate) < 0) {
            return false;
        }

        return true;
    }
}
