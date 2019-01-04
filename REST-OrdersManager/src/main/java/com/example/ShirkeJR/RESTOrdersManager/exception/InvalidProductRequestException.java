package com.example.ShirkeJR.RESTOrdersManager.exception;

public class InvalidProductRequestException extends RuntimeException{

    public InvalidProductRequestException() {
        super("Invalid Product Request Exception");
    }

    public InvalidProductRequestException(String message) {
        super(message);
    }
}
