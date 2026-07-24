package com.example.WebChat.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CaretPosition {

    @JsonProperty("PosStart")
    private int pos1;

    @JsonProperty("PosEnd")
    private int pos2;
}
