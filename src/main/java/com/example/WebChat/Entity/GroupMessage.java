package com.example.WebChat.Entity;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

@Table
@Entity(name = "group_message")
public class GroupMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String message;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private Users sender;

    @ManyToOne
    @JoinColumn(name = "group_chat_group_chat_id")
    private GroupChat groupChat;

    public GroupMessage(Users sender, GroupChat groupChat, String message) {
        this.sender = sender;
        this.groupChat = groupChat;
        this.message = message;
    }
    public GroupMessage() {}

    public GroupChat getGroupChat() {
        return groupChat;
    }

    public void setGroupChat(GroupChat groupChat) {
        this.groupChat = groupChat;
    }

    public Users getSender() {
        return sender;
    }

    public void setSender(Users sender) {
        this.sender = sender;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public GroupMessage setMessage(String message) {
        this.message = message;
        return this;
    }
}
