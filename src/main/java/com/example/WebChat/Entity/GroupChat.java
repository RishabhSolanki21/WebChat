package com.example.WebChat.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "group_chat")
public class GroupChat {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long groupChat_Id;

    private String groupChat_Name;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users users;

    public GroupChat(String groupChat_Name, Users users) {
        this.groupChat_Name = groupChat_Name;
        this.users = users;
    }
    public GroupChat() {}

    public Long getGroupChat_Id() {
        return groupChat_Id;
    }

    public String getGroupChat_Name() {
        return groupChat_Name;
    }

    public GroupChat setGroupChat_Name(String groupChat_Name) {
        this.groupChat_Name = groupChat_Name;
        return this;
    }

    public Users getUsers() {
        return users;
    }

    public GroupChat setUsers(Users users) {
        this.users = users;
        return this;
    }
}
