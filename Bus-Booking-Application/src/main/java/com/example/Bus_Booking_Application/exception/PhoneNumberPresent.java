package com.example.Bus_Booking_Application.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class PhoneNumberPresent extends RuntimeException{
    private String message;

    public PhoneNumberPresent(String message) {
        super(message);
    }
}
