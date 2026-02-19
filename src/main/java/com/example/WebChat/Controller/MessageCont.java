package com.example.WebChat.Controller;

import com.example.WebChat.Dto.GroupDto;
import com.example.WebChat.Entity.PrivateChat;
import com.example.WebChat.Dto.PrivateMessageDto;
import com.example.WebChat.Entity.PrivateMessage;
import com.example.WebChat.Entity.Users;
import com.example.WebChat.Repository.PvtMessageRepo;
import com.example.WebChat.Repository.UserRepo;
import com.example.WebChat.Repository.ChatRepo;
import com.example.WebChat.Service.ChatRepoAccess;
import com.example.WebChat.Service.PvtMessageRepoAccess;
import com.example.WebChat.Service.UserRepoAccess;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class MessageCont {

    private static final Logger log = LoggerFactory.getLogger(MessageCont.class);
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final UserRepoAccess userRepo;
    private final ChatRepoAccess chatRepo;
    private final PvtMessageRepoAccess messageRepo;
    MessageCont(UserRepoAccess userRepo, SimpMessagingTemplate simpMessagingTemplate,
                ChatRepoAccess chatRepo, PvtMessageRepoAccess messageRepo) {
        this.userRepo = userRepo;
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.chatRepo = chatRepo;
        this.messageRepo = messageRepo;
    }

    @MessageMapping("/message/{roomid}")
    public void groupmessage(@Payload GroupDto dto, @DestinationVariable String roomid) throws InterruptedException {
        log.info(String.valueOf(dto));
//        Thread.sleep(2000);
        log.info("roomid: {} with message {}", roomid, dto.getMessage());
        simpMessagingTemplate.convertAndSend("/topic/group/" + roomid, dto);
    }
    @MessageMapping("/private/message")
    @Transactional
    public void privatemessage(@Payload PrivateMessageDto messageCont, Principal principal) throws InterruptedException {
        {
            String senderName = principal.getName();
            Users sender = userRepo.findByUsername(senderName);
            Users receiver = userRepo.findByUsername(messageCont.getReceivername());
            PrivateChat chat = chatRepo.findChatByUsers(sender, receiver);
            PrivateMessage message = new PrivateMessage(sender, chat, messageCont.getMessage());
            messageRepo.save(message);
        }
        simpMessagingTemplate.convertAndSendToUser(
                messageCont.getReceivername(), "/private", messageCont );
    }
}
