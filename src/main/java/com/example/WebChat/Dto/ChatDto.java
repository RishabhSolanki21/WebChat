package com.example.WebChat.Dto;

import com.example.WebChat.Entity.Message;

import java.util.List;

public class ChatDto {

    private String friends;
    private List<MessageDto> messageList;

    public ChatDto(String friends, List<MessageDto> messageList) {
        this.friends = friends;
        this.messageList = messageList;
    }

    public ChatDto() {
    }

    public String getFriends() {
        return friends;
    }
    public void setFriends(String friends) {
        this.friends = friends;
    }
    public List<MessageDto> getMessageList() {
        return messageList;
    }
    public void setMessageList(List<MessageDto> messageList) {
        this.messageList = messageList;
    }
    @Override
    public String toString() {
        return "ChatDto{" +
                ", friends='" + friends + '\'' +
                ", messages='" + messageList + '\'' +
                '}';
    }
}
