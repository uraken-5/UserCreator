package com.jcarmona.config.exception;

public class UserEmailAlreadyExistsException extends RuntimeException{
    public UserEmailAlreadyExistsException(String message) {
        super(message);
    }
}
