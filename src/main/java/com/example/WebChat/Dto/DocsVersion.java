package com.example.WebChat.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class DocsVersion {

    @JsonProperty("version")
    private int version;

    @JsonProperty("content")
    private String Docs;

}
