package com.kodizim.findaduck.api.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.kodizim.findaduck.error.ApiError;
import lombok.Getter;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
public class ErrorResponse {
    private List<ApiError> errors;

    public static ErrorResponse of(String code, String message, Object detail) {
        return of(new ApiError(code, message, detail));
    }
    public static ErrorResponse of(String code, String message) {
        return of(new ApiError(code, message, null));
    }

    public static ErrorResponse of(ApiError error) {
        return of(List.of(error));
    }

    public static ErrorResponse of(List<ApiError> errors) {
        var response = new ErrorResponse();
        response.errors = List.copyOf(errors);
        return response;
    }
}
