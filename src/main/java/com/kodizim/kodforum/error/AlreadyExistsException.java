package com.kodizim.kodforum.error;

public class AlreadyExistsException extends ApiException {
    public AlreadyExistsException(String message) {
        super(ErrorCode.ALREADY_EXISTS, message);
    }
}
