package com.zerutis.codingchallenge.helper;

import com.zerutis.codingchallenge.exception.InvalidDateException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateHelper {

    public static boolean isValidDate(String date) {
        try {
            LocalDate.parse(date, DateTimeFormatter.ISO_DATE);
        } catch (DateTimeParseException e) {
            throw new InvalidDateException(date);
        }
        return true;
    }
}
