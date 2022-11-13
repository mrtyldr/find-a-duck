package com.kodizim.kodforum.error;

public class InProgressException extends ApiException {
    public InProgressException(String message) {
        super(ErrorCode.IN_PROGRESS, message);
    }
}
