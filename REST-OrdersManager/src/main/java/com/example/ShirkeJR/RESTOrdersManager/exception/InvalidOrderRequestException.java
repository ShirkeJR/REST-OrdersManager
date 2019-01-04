package com.example.ShirkeJR.RESTOrdersManager.exception;

public class InvalidOrderRequestException extends RuntimeException{

    public InvalidOrderRequestException() {
        super("Invalid Order Request Exception");
    }

    public InvalidOrderRequestException(String message) {
        super(message);
    }
}
