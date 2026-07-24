package com.example.WebChat.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PrivateMessageDto implements Serializable {

    @JsonProperty("messageId")
    Long id;

    @JsonProperty("sendername")
    String senderName;

    @JsonProperty("message")
    String message;

    @JsonProperty("receivername")
    String receiverName;

    @JsonProperty("hasNext")
    Boolean hasNext;

    @JsonProperty("mType")
    MessageType messageType;

    LocalDateTime timestamp;
}
