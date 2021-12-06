package com.demo.parkinglot.exception;

public class SlotException extends RuntimeException{

    private String message;

    public SlotException(String message) {
        super(message);
        this.message = message;
    }

    public String getMessage(){
        return this.message;
    }
}


