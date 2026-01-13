package com.example.WebChat.Entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "chatusers")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique = true)
    private String username;

    @ElementCollection
    @JoinTable(
            name = "friends",
            joinColumns = @JoinColumn(name = "user_id")
    )
    @Column(name = "friendsname")
    private Set<String> friendsname=new HashSet<>();
    private String password;
    @ElementCollection
    @JoinTable(
            name = "content",
            joinColumns = @JoinColumn(name = "user_id")
    )
    @Column(name = "messages")
    private List <String> content=new ArrayList<>();
    public Users() {
    }

    public String getPassword() {
        return password;
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

    public List<String> getContent() {
        return content;
    }
    public String getUsername() {
        return username;
    }
        public void setUsername(String username) {
            this.username = username;
        }
        public void setPassword(String password) {
            this.password = password;
        }
            public void setFriendsname(String senderName) {
            this.friendsname.add(senderName);
        }

}
