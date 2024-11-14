package com.jcarmona.config.exception;

public class InvalidAuthorizationHeaderException extends RuntimeException {

    public InvalidAuthorizationHeaderException(String message) {
        super(message);
    }

}