package com.devsu.customer_service.exception;

public class CustomerConflictException extends RuntimeException{

    public CustomerConflictException(String message){
        super(message);
    }
}
