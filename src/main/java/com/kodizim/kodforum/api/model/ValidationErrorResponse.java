package com.kodizim.kodforum.api.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.kodizim.kodforum.error.ValidationApiError;
import lombok.Getter;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
public class ValidationErrorResponse {

    private List<ValidationApiError> errors;

    public static ValidationErrorResponse of(String message) {
        return of(new ValidationApiError(message));
    }

    public static ValidationErrorResponse of(ValidationApiError error) {
        return of(List.of(error));
    }

    public static ValidationErrorResponse of(List<ValidationApiError> errors) {
        var response = new ValidationErrorResponse();
        response.errors = List.copyOf(errors);
        return response;
    }
}
