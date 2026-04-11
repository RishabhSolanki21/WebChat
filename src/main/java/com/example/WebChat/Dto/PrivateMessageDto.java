package com.example.WebChat.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PrivateMessageDto {

    @JsonProperty("messageId")
    Long id;

    @JsonProperty("sendername")
    String sendername;

    @JsonProperty("message")
    String message;

    @JsonProperty("receivername")
    String receivername;

    @JsonProperty("hasNext")
    Boolean hasNext;

    LocalDateTime timestamp;
}
