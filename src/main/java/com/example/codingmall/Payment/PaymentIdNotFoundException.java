package com.example.codingmall.Payment;

public class PaymentIdNotFoundException extends RuntimeException {
    public PaymentIdNotFoundException(String message) {
        super(message);
    }
}
