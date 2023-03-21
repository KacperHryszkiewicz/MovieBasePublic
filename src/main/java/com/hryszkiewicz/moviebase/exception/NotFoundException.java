package com.hryszkiewicz.moviebase.exception;

public class NotFoundException extends RuntimeException{
    public NotFoundException(String message) {
        super(message);
    }
}
