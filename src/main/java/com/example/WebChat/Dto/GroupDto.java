package com.example.WebChat.Dto;

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

    @JsonProperty("caret")
    private CaretPosition caretPosition;

    @JsonProperty("roomId")
    private Long Project_id;

    @JsonProperty("version")
    private int version;

    @JsonProperty("oldPos")
    private int oldPosition;

    @JsonProperty("changed_text")
    private ChangedText changedText;

}
