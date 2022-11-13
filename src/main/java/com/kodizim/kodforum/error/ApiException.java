package com.kodizim.kodforum.error;


import lombok.Getter;

@Getter
public class ApiException extends RuntimeException {

    private final ErrorCode code;
    private final String message;

    private final Object detail;

    public ApiException(ErrorCode code, String message, Object detail) {
        super(message);
        this.code = code;
        this.message = message;
        this.detail = detail;
    }

    public ApiException(ErrorCode code, String message) {
        this(code, message, null);
    }
}
