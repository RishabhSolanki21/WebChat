package com.example.WebChat.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "group_chat")
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class GroupChat {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name="Project_id")
    private Project_data projectData;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users users;

    private String message;

    private LocalDateTime created_at;

}
