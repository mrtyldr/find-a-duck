package com.kodizim.findaduck.error;

public class AlreadyExistsException extends ApiException {
    public AlreadyExistsException(String message) {
        super(ErrorCode.ALREADY_EXISTS, message);
    }
}
