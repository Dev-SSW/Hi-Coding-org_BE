package com.example.codingmall.Exception;

public class PlantNotFoundException extends RuntimeException {
    public PlantNotFoundException (String message){
        super(message);
    }
}
