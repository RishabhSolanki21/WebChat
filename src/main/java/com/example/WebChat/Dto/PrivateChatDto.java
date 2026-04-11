package com.example.WebChat.Dto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PrivateChatDto {

    @JsonProperty("friends")
    private String friends;
    @JsonProperty("chatId")
    Long chatId;
    @JsonProperty("MessageList")
    private List<PrivateMessageDto> messageList;

}
