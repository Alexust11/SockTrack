package com.example.socktrack.exception;

public class SocksNotFoundException extends RuntimeException{
    public SocksNotFoundException(String message) {
        super(message);
    }
}
