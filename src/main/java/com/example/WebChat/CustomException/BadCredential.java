package com.example.WebChat.CustomException;

public class BadCredential extends RuntimeException {
    public BadCredential(String msg) {
        super(msg);
    }
}
