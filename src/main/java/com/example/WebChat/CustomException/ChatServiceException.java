package com.example.WebChat.CustomException;

public class ChatServiceException extends RuntimeException{

    public ChatServiceException(String message) {
        super(message);
    }
}
