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


}
