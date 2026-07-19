package com.example.WebChat.Dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OnlineUsers {

    private String username;
    private States state;
    private String RoomId;


}
