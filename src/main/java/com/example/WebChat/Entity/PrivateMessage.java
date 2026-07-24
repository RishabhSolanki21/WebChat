package com.example.WebChat.Entity;


import com.example.WebChat.Dto.MessageType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Table(name = "private_messages")
@Entity
@Setter
@Getter
@ToString
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

    @JoinColumn(name = "message_type")
    @Enumerated(EnumType.STRING)
    private MessageType type;

    @CreationTimestamp
    @Column(nullable = false,updatable = false)
    private LocalDateTime time;

    public PrivateMessage(Users sender, PrivateChat chat, String message,MessageType messageType) {
        this.sender=sender;
        this.chat=chat;
        this.content=message;
        this.type=messageType;
    }

    public PrivateMessage() {
    }
}
