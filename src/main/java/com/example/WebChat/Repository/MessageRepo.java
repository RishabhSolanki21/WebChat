package com.example.WebChat.Repository;

import com.example.WebChat.Entity.PrivateMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepo extends JpaRepository<PrivateMessage,Long> {

    List<PrivateMessage> findByChat_ChatId(Long chatChatId);

}
