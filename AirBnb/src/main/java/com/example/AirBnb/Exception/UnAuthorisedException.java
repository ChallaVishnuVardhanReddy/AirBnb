package com.example.AirBnb.Exception;

public class UnAuthorisedException extends RuntimeException{
    public UnAuthorisedException(String message){
        super(message);
    }
}
