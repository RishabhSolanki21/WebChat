package com.example.WebChat.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ChangedText {

    @JsonProperty("newText")
    private String newText;

    @JsonProperty("start")
    private int start;

    @JsonProperty("delete_count")
    private int delete_count;

    @JsonProperty("version")
    private int version;

}
//@JsonProperty("username")
////    private String username;
////
//@JsonProperty("content")
//private String content;
//
//    @JsonProperty("type")
//    private MessageType type;

//    @JsonProperty("caret")
//    private CaretPosition caretPosition;

//    @JsonProperty("roomId")
//    private Long Project_id;

//    @JsonProperty("version")
//    private int version;
//
//    @JsonProperty("changed_text")
//    private ChangedText changedText;
