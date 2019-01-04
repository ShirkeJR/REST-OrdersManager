package com.example.ShirkeJR.RESTOrdersManager.exception;

public class InvalidCustomerRequestException extends RuntimeException{

    public InvalidCustomerRequestException() {
        super("Invalid Customer Request Exception");
    }

    public InvalidCustomerRequestException(String message) {
        super(message);
    }
}
