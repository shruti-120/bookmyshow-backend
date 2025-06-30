package com.bookmyshow.utils.exceptionHandlers;

public class InvalidTokenException extends RuntimeException {
    public InvalidTokenException(String message) {
        super(message);
    }
}
