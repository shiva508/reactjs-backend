package com.comrade.reactjsbackend.model.exception;

public class JwtCustomException extends RuntimeException{
    public JwtCustomException(String message) {
        super(message);
    }
}
