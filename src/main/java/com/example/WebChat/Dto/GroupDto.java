package com.example.WebChat.Dto;

import com.example.WebChat.Entity.MessageType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GroupDto {

    @JsonProperty("username")
    private String username;

    @JsonProperty("content")
    private String message;

    @JsonProperty("type")
    private MessageType type;

    @JsonProperty("PosStart")
    private int pos;

    @JsonProperty("PosEnd")
    private int pos2;

    @JsonProperty("roomId")
    private Long Project_id;

}
