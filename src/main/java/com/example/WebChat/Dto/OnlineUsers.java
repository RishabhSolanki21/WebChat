package com.example.WebChat.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OnlineUsers {


    @JsonProperty("username")
    private String username;
    @JsonProperty("state")
    private States state;
    @JsonProperty("roomId")
    private String RoomId;

    private String SessionId;

    @JsonProperty("type")
    private MessageType MessageType;

    @JsonProperty("caret")
    private CaretPosition caretPosition;

//    @JsonProperty("colour")
//    private int user_colour;
}
