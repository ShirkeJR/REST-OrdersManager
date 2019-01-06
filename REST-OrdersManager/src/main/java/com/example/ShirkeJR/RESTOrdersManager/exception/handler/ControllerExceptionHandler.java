package com.example.ShirkeJR.RESTOrdersManager.exception.handler;

import com.example.ShirkeJR.RESTOrdersManager.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND) // 404
    @ExceptionHandler(CustomerNotFoundException.class)
    public void handleNotFound(CustomerNotFoundException ex) {
        log.error("Customer not found");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST) // 400
    @ExceptionHandler(InvalidCustomerRequestException.class)
    public void handleBadRequest(InvalidCustomerRequestException ex) {
        log.error("Invalid Customer Request");
    }

    @ResponseStatus(HttpStatus.NOT_FOUND) // 404
    @ExceptionHandler(OrderNotFoundException.class)
    public void handleNotFound(OrderNotFoundException ex) {
        log.error("Order not found");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST) // 400
    @ExceptionHandler(InvalidOrderRequestException.class)
    public void handleBadRequest(InvalidOrderRequestException ex) {
        log.error("Invalid Order Request");
    }

    @ResponseStatus(HttpStatus.NOT_FOUND) // 404
    @ExceptionHandler(ProductNotFoundException.class)
    public void handleNotFound(ProductNotFoundException ex) {
        log.error("Product not found");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST) // 400
    @ExceptionHandler(InvalidProductRequestException.class)
    public void handleBadRequest(InvalidProductRequestException ex) {
        log.error("Invalid Product Request");
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) // 500
    @ExceptionHandler(Exception.class)
    public void handleGeneralError(Exception ex) {
        log.error("An error occurred procesing request", ex);
    }
}