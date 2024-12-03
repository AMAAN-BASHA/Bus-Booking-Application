package com.example.Bus_Booking_Application.exception;

public class DuplicateBusNumberException extends RuntimeException {
    public DuplicateBusNumberException(String message) {
        super(message);
    }
}
