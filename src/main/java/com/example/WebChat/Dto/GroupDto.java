package com.example.WebChat.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GroupDto {

    @JsonProperty("roomId")
    private String groupName;

    @JsonProperty("username")
    private String username;

    @JsonProperty("content")
    private String message;

}
