package com.example.WebChat.Repository;

import com.example.WebChat.Entity.Chat;
import com.example.WebChat.Entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface chatRepo extends JpaRepository<Chat,Long> {

    Optional<Chat> findChatByUsers1AndUsers2(Users users1, Users users2);
    Optional<Chat> findChatByUsers1OrUsers2(Users users1, Users users2);
}
