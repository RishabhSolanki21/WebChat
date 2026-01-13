package com.example.WebChat.Entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "Chats",
        uniqueConstraints = @UniqueConstraint(columnNames = {"users_1_id","users_2_id"})
)
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long chat_id;

    @ManyToOne
    @JoinColumn(name = "users_1_id")
    private Users users1;

    @ManyToOne
    @JoinColumn(name = "users_2_id")
    private Users users2;

    @CreationTimestamp
    @Column(nullable = false,updatable = false)
    private LocalDateTime time;

    public Chat(Users sender, Users receiver) {
        if (sender.getId()< receiver.getId()){
            this.users1=sender;
            this.users2=receiver;
        }
        else {
            this.users2=sender;
            this.users1=receiver;
        }
    }

    public Chat() {

    }

    public Users getUsers2() {
        return users2;
    }

    public void setUsers2(Users users2) {
        this.users2 = users2;
    }

    public Users getUsers1() {
        return users1;
    }

    public void setUsers1(Users users1) {
        this.users1 = users1;
    }

    public void setChat_id(Long chatId) {
        this.chat_id = chatId;
    }

    public Long getChat_id() {
        return chat_id;
    }
}
