package com.example.WebChat.Entity;


import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Table(name = "private_messages")
@Entity
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

    public Users getSender() {
        return sender;
    }

    public void setSender(Users sender) {
        this.sender = sender;
    }
    public PrivateChat getChat() {
        return chat;
    }
    public void setChat(PrivateChat chat) {
        this.chat = chat;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public LocalDateTime getTime() {
        return time;
    }
    public void setTime(LocalDateTime time) {
        this.time = time;
    }

}
