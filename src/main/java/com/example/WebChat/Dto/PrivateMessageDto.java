package com.example.WebChat.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

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
    String senderName;

    @JsonProperty("message")
    String message;

    @JsonProperty("receivername")
    String receiverName;

    @JsonProperty("hasNext")
    Boolean hasNext;

    @JsonProperty("file")
    MultipartFile file;

    LocalDateTime timestamp;
}
