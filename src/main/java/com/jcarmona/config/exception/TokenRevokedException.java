package com.jcarmona.config.exception;

public class TokenRevokedException extends RuntimeException{
    
	private static final long serialVersionUID = 1L;

	public TokenRevokedException(String message) {
        super(message);
    }
}
