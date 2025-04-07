package com.devsu.account_service.exception;

public class MovementNotFoundException extends RuntimeException{

    public MovementNotFoundException(String message){
        super(message);
    }
}
