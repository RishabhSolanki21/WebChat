package com.example.WebChat.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Table(name = "Project-Data")
@Entity
@Builder
@Setter
@Getter
@ToString
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

    @ManyToOne
    @JoinColumn(name = "last_updater")
    private Users last_updater;

}
