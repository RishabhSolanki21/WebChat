package com.example.WebChat.Service;

import com.example.WebChat.Entity.PrivateMessage;
import com.example.WebChat.Repository.PvtMessageRepo;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class PvtMessageRepoAccess {

    private final PvtMessageRepo repo;
    private final CacheManager cacheManager;
    PvtMessageRepoAccess(PvtMessageRepo repo, CacheManager cacheManager) {
        this.repo = repo;
        this.cacheManager = cacheManager;
    }
    @Async
    public void save(String username,String receiver,PrivateMessage message) {
        repo.save(message);
        Objects.requireNonNull(cacheManager.getCache("friends")).evict(username);
        Objects.requireNonNull(cacheManager.getCache("friends")).evict(receiver);
    }
}
