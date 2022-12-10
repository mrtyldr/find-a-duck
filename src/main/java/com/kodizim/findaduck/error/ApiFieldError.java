package com.kodizim.findaduck.error;

import lombok.Value;

@Value
public class ApiFieldError {
    String field;
    String errorType;
    String message;

    public static ApiFieldError of(String field, ValidationErrorCode error, String message) {
        return new ApiFieldError(field, error.name(), message);
    }
}
