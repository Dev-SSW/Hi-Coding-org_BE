package com.example.codingmall.Exception;

public class PaymentAlreadyHasException  extends RuntimeException{
    public PaymentAlreadyHasException(String message){
        super(message);
    }
}
