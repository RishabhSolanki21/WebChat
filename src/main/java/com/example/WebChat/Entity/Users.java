package com.example.WebChat.Entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "chatusers")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    @Column(unique = true)
    String username;

    @ElementCollection
    @JoinTable(
            name = "friends",
            joinColumns = @JoinColumn(name = "user_id")
    )
    @Column(name = "friendsname")
    List<String>friendsname=new ArrayList<>();
    String password;
    String content;
    public Users() {
    }

    public String getPassword() {
        return password;
    }

    public Users(Builder builder) {
        this.username = builder.username;
        this.password = builder.password;
        this.friendsname = builder.friendsname;
        this.content = builder.content;
    }
//    // Getters and Setters
    public Long getId() {
        return id;
    }

    public String getFriendName(String friendName) {
        return friendsname.stream()
                .filter(name -> name.equals(friendName))
                .findFirst()
                .orElse(null);
    }
    public void setFriendName() {
        setFriendName();
    }



    public String getContent() {
        return content;
    }
    public String getUsername() {
        return username;
    }
    public static class Builder {

        Long id;
        String username;

        List<String>friendsname=new ArrayList<>();
        String password;
        String content;

        public Builder setUsername(String username) {
            this.username = username;
            return this;
        }
        public Builder setPassword(String password) {
            this.password = password;
            return this;
        }
            public Builder setFriendsname(String senderName) {
            this.friendsname.add(senderName);
            return this;
        }
            public Builder setContent(String content) {
            this.content = content;
            return this;
        }

        public Users build() {
            Users users = new Users(this);
            return users;
        }
    }
}
