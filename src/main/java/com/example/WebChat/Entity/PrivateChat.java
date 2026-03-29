package com.example.WebChat.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "private_chats",
        uniqueConstraints = @UniqueConstraint(columnNames = {"users_1_id","users_2_id"})
)
@Getter
@Setter
public class PrivateChat {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long chatId;

    @ManyToOne
    @JoinColumn(name = "users_1_id")
    private Users users1;

    @ManyToOne
    @JoinColumn(name = "users_2_id")
    private Users users2;

    @CreationTimestamp
    @Column(nullable = false,updatable = false)
    private LocalDateTime time;

    public PrivateChat(Users sender, Users receiver) {
        if (sender.getId()< receiver.getId()){
            this.users1=sender;
            this.users2=receiver;
        }
        else {
            this.users2=sender;
            this.users1=receiver;
        }
    }

    public PrivateChat() {

    }
}
