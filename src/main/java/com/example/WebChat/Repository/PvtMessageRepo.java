package com.example.WebChat.Repository;

import com.example.WebChat.Entity.PrivateMessage;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface PvtMessageRepo extends JpaRepository<PrivateMessage,Long> {

    List<PrivateMessage> findByChat_ChatId(Long chatChatId);

    @Query("""
    SELECT m FROM PrivateMessage m
    WHERE m.chat.chatId = :chatId
    AND (:cursor IS NULL OR m.messageId<=:cursor)
    ORDER BY m.messageId DESC 
""")
    List<PrivateMessage> findPrivateMessage(@Param("chatId" )Long chatId, @Param("cursor") Long cursor, Pageable pagable );
}
