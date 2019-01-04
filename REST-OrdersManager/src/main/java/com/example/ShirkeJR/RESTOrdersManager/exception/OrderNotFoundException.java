package com.example.ShirkeJR.RESTOrdersManager.exception;

public class OrderNotFoundException extends RuntimeException{

    public OrderNotFoundException() {
        super("Order Not Found Exception");
    }

    public OrderNotFoundException(String message) {
        super(message);
    }
}
