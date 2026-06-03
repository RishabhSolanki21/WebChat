package com.example.WebChat.Service;

import com.example.WebChat.Entity.PrivateMessage;
import com.example.WebChat.Repository.ChatRepo;
import com.example.WebChat.Repository.PvtMessageRepo;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

@Service
//@EnableAsync
public class PvtMessageRepoAccess {


    private final PvtMessageRepo repo;
    PvtMessageRepoAccess(PvtMessageRepo repo) {
        this.repo = repo;
    }
    @Async
    @CacheEvict(
            value = "friends",
            key = "#username"
    )
    public void save(String username,PrivateMessage message) {
        repo.save(message);
    }
}
