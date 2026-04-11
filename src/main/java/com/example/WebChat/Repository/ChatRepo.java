package com.example.WebChat.Repository;

import com.example.WebChat.Entity.PrivateChat;
import com.example.WebChat.Entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ChatRepo extends JpaRepository<PrivateChat,Long> {

    @Query("SELECT c FROM PrivateChat c WHERE " +
            "(c.users1 = :user1 AND c.users2 = :user2) OR " +
            "(c.users1 = :user2 AND c.users2 = :user1)")
    Optional<PrivateChat> findChatByUsers(@Param("user1") Users user1,
                                          @Param("user2") Users user2);


    List<PrivateChat> findChatByUsers1OrUsers2(Users users1, Users users2);
    PrivateChat findByChatId(Long chatId);
}
