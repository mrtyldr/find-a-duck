package com.kodizim.kodforum.error;

import lombok.Getter;

import javax.validation.ConstraintViolation;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ValidationException extends ApiException {
    @Getter
    private final List<ApiFieldError> fieldErrors;
    @Getter
    private final Set<ConstraintViolation<?>> constraintViolations;

    public ValidationException(List<ApiFieldError> fieldErrors) {
        this(fieldErrors, Set.of());
    }
    public ValidationException(Set<? extends ConstraintViolation<?>> constraintViolations) {
        this(List.of(), constraintViolations);
    }

    public ValidationException(List<ApiFieldError> fieldErrors, Set<? extends ConstraintViolation<?>> constraintViolations) {
        this("Some fields does not have valid values.", fieldErrors, constraintViolations);
    }

    public ValidationException(String message, List<ApiFieldError> fieldErrors, Set<? extends ConstraintViolation<?>> constraintViolations) {
        super(ErrorCode.VALIDATION, message);
        this.fieldErrors = fieldErrors;
        this.constraintViolations = new HashSet<>(constraintViolations);

    }

    public ValidationException(String message, List<ApiFieldError> fieldErrors) {
        this(message, fieldErrors, Set.of());

    }

    public ValidationException(String message, Set<? extends ConstraintViolation<?>> constraintViolations) {
        this(message, List.of(), constraintViolations);

    }

    public ValidationException(String message) {
        this(message, List.of(), Set.of());
    }
}
