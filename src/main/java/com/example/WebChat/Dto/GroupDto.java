package com.example.WebChat.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GroupDto {

    @JsonProperty("roomId")
    private String groupName;

    @JsonProperty("username")
    private String username;

    @JsonProperty("content")
    private String message;

    public GroupDto(String groupName, String username, String message) {
        this.groupName = groupName;
        this.username = username;
        this.message = message;
    }
    public GroupDto() {}
    public String getGroupName() {
        return groupName;
    }

    public GroupDto setGroupName(String groupName) {
        this.groupName = groupName;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public GroupDto setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public GroupDto setMessage(String message) {
        this.message = message;
        return this;
    }
}
