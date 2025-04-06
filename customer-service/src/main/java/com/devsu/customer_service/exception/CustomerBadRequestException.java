package com.devsu.customer_service.exception;

public class CustomerBadRequestException extends RuntimeException{

    public CustomerBadRequestException(String message){
        super(message);
    }
}
