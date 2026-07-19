package com.example.WebChat.Service;


import com.example.WebChat.Entity.PrivateMessage;
import com.example.WebChat.Repository.PvtMessageRepo;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class MessageRepoAccess {

    private final PvtMessageRepo repo;

    public List<PrivateMessage> findByChat_ChatId(Long chatId, int pageSize, Long cursor) {
        Pageable pageable = PageRequest.of(0, pageSize);
        return repo.findPrivateMessage(chatId,cursor,pageable);

    }

}
