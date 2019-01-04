package com.example.ShirkeJR.RESTOrdersManager.exception;

public class CustomerNotFoundException extends RuntimeException{

    public CustomerNotFoundException() {
        super("Customer Not Found Exception");
    }

    public CustomerNotFoundException(String message) {
        super(message);
    }
}
