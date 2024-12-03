package com.example.Bus_Booking_Application.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResouceNotFoundException extends RuntimeException {
    private String resouceName;
    private String fieldName;
    private Long FieldId;

    public ResouceNotFoundException(String resouceName, String fieldName, Long fieldId) {
        super(String.format("%s not found with %s : %s",resouceName,fieldName,fieldId));
        this.resouceName = resouceName;
        this.fieldName = fieldName;
        this.FieldId = fieldId;
    }
}