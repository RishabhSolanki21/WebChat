package com.example.WebChat.Service;

import com.example.WebChat.Entity.PrivateMessage;
import com.example.WebChat.Repository.ChatRepo;
import com.example.WebChat.Repository.PvtMessageRepo;
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
    public void save(PrivateMessage message) {
        repo.save(message);
    }
}
