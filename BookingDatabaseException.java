package com.example.javafxx;

public class BookingDatabaseException extends Exception {
    public BookingDatabaseException(String message) {
        super(message);
    }

    public BookingDatabaseException(String message, Throwable cause) {
        super(message, cause);
    }
}
