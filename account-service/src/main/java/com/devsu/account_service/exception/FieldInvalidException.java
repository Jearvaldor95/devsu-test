package com.devsu.account_service.exception;

public class FieldInvalidException extends BadRequestException{

    public FieldInvalidException(String message){
        super(message);
    }
}
