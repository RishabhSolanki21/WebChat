package com.example.WebChat.Entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Table(name = "private_messages")
@Entity
@Setter
@Getter
public class PrivateMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long messageId;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private Users sender;

    @ManyToOne
    @JoinColumn(name = "Chat_id")
    private PrivateChat chat;
    @Column(name = "Message",nullable = false)
    private String content;

    @CreationTimestamp
    @Column(nullable = false,updatable = false)
    private LocalDateTime time;

    public PrivateMessage(Users sender, PrivateChat chat, String message) {
        this.sender=sender;
        this.chat=chat;
        this.content=message;

    }

    public PrivateMessage() {
    }
}
