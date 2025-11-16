package com.example.WebChat.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Messagesof {

    @JsonProperty("sendername")
    String sendername;

    @JsonProperty("message")
    String message;

    @JsonProperty("receivername")
    String receivername;

    public Messagesof() {
    }

    public Messagesof(String sendername, String message, String receivername) {
        this.sendername = sendername;
        this.message = message;
        this.receivername = receivername;
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
    @Override
    public String toString() {
        return "Messages{" +
                "sendername='" + sendername + '\'' +
                ", message='" + message + '\'' +
                ", receivername='" + receivername + '\'' +
                '}';
    }
}
