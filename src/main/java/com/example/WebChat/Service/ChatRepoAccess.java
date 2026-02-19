package com.example.WebChat.Service;
import com.example.WebChat.CustomException.ChatNotFoundException;
import com.example.WebChat.CustomException.ChatServiceException;
import com.example.WebChat.Entity.PrivateChat;
import com.example.WebChat.Entity.Users;
import com.example.WebChat.Repository.ChatRepo;

import org.springframework.dao.DataAccessException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatRepoAccess {

    private final ChatRepo chatRepo;

    public ChatRepoAccess(ChatRepo chatRepo) {
        this.chatRepo = chatRepo;
    }

    public void save(PrivateChat chat) {
        chatRepo.save(chat);
    }

    public List<PrivateChat> findChatByUsers1OrUsers2(Users user1, Users user2) {
        try {
           List<PrivateChat>chats= chatRepo.findChatByUsers1OrUsers2(user1, user2);
           if (chats==null||chats.isEmpty()) {
               throw new ChatNotFoundException("No chats found");
           }
           return chats;
        }catch (DataAccessException e){
            throw new ChatServiceException("there is something went wrong "+e);
        }
    }

    public PrivateChat findChatByUsers(Users sender,Users receiver){
        return chatRepo.findChatByUsers(sender,receiver).orElseGet(()->
        chatRepo.save(new PrivateChat(sender,receiver)));
    }
}
