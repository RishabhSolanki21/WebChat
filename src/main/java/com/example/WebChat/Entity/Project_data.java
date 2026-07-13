package com.example.WebChat.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Table(name = "Project-Data")
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Project_data {

    @Id
    private Long Project_id;

    private String document;

    private LocalDateTime date;

    @ManyToOne
    @JoinColumn(name = "creator")
    private Users creator;

}
