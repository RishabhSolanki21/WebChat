package com.example.WebChat.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class PrivateMessageDto {

    @JsonProperty("sendername")
    String sendername;

    @JsonProperty("message")
    String message;

    @JsonProperty("receivername")
    String receivername;

    LocalDateTime timestamp;

    public PrivateMessageDto() {
    }

    public PrivateMessageDto(String sendername, String message, String receivername, LocalDateTime timestamp) {
        this.sendername = sendername;
        this.message = message;
        this.receivername = receivername;
        this.timestamp = timestamp;
    }
    public String getSendername() {
        return sendername;
    }
    public void setSendername(String sendername) {
        this.sendername = sendername;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public String getReceivername() {
        return receivername;
    }
    public void setReceivername(String receivername) {
        this.receivername = receivername;
    }
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
    @Override
    public String toString() {
        return "Messages{" +
                "sendername='" + sendername + '\'' +
                ", message='" + message + '\'' +
                ", receivername='" + receivername + '\'' +
                '}';
    }
}
