package com.example.WebChat.Dto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PrivateChatDto implements Serializable {

    @JsonProperty("friends")
    private String friends;
    @JsonProperty("chatId")
    Long chatId;
    @JsonProperty("MessageList")
    private List<PrivateMessageDto> messageList;

}
