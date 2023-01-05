package com.kodizim.findaduck.error;

public class WrongUserNamePasswordException extends RuntimeException{
    public WrongUserNamePasswordException(String message) {
        super(message);
    }
}
