package com.example.WebChat.Repository;

import com.example.WebChat.Entity.Chat;
import com.example.WebChat.Entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface chatRepo extends JpaRepository<Chat,Long> {

    @Query("SELECT c FROM Chat c WHERE " +
            "(c.users1 = :user1 AND c.users2 = :user2) OR " +
            "(c.users1 = :user2 AND c.users2 = :user1)")
    Optional<Chat> findChatByUsers(@Param("user1") Users user1,
                                   @Param("user2") Users user2);
//    Optional<Chat> findChatByUsers1AndUsers2(Users users1, Users users2);
    List<Chat> findChatByUsers1OrUsers2(Users users1, Users users2);
}
