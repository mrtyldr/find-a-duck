package com.kodizim.kodforum.error;

import lombok.Getter;

import java.util.Collection;
import java.util.List;

public class ValidationApiError extends ApiError {

    @Getter
    private final List<ApiFieldError> details;

    public ValidationApiError(String message) {
        super(ErrorCode.VALIDATION.name(), message);
        this.details = List.of();
    }

    public ValidationApiError(String message, Collection<ApiFieldError> details) {
        super(ErrorCode.VALIDATION.name(), message);
        this.details = List.copyOf(details);
    }
}
