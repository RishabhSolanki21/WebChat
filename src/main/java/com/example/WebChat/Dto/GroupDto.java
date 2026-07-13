package com.example.WebChat.Dto;

import com.example.WebChat.Entity.MessageType;
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

    @JsonProperty("username")
    private String username;

    @JsonProperty("content")
    private String message;

    @JsonProperty("type")
    private MessageType type;

    @JsonProperty("Pos")
    private int pos;

    @JsonProperty("roomId")
    private Long Project_id;

}
