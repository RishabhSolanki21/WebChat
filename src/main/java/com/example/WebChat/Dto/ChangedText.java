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

}
