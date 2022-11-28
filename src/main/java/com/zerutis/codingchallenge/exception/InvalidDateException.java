package com.zerutis.codingchallenge.exception;

public class InvalidDateException extends InvalidArgumentException {
    public InvalidDateException(String date) {
        super(String.format("Invalid date format: %s", date));
    }
}
