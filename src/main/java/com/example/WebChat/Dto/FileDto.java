package com.example.WebChat.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class FileDto {

    @JsonProperty("fileName")
    String fileName;
    @JsonProperty("fileType")
    String fileType;
}
