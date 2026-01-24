package com.example.WebChat.Repository;

import com.example.WebChat.Entity.Message;
import org.springframework.data.domain.Limit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepo extends JpaRepository<Message,Long> {

    List<Message> findByChat_ChatId(Long chatChatId);

}
