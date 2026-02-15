package com.example.WebChat.Dto;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;


public class PrivateChatDto {

    @JsonProperty("friends")
    private String friends;
    @JsonProperty("MessageList")
    private List<PrivateMessageDto> messageList;

    public PrivateChatDto(String friends, List<PrivateMessageDto> messageList) {
        this.friends = friends;
        this.messageList = messageList;
    }

    public PrivateChatDto() {
    }

    public String getFriends() {
        return friends;
    }
    public void setFriends(String friends) {
        this.friends = friends;
    }
    public List<PrivateMessageDto> getMessageList() {
        return messageList;
    }
    public void setMessageList(List<PrivateMessageDto> messageList) {
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
