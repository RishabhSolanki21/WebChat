package com.example.WebChat.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class RoomEvent {

    @JsonProperty("username")
    private String username;

    @JsonProperty("payload")
    private JsonNode payload;

    @JsonProperty("type")
    private MessageType type;

    @JsonProperty("roomId")
    private Long Project_id;
}
