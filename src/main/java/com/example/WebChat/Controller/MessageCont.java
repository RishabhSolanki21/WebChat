package com.example.WebChat.Controller;

import com.example.WebChat.Configurations.Dispatcher;
import com.example.WebChat.Dto.GroupDto;
import com.example.WebChat.Entity.PrivateChat;
import com.example.WebChat.Dto.PrivateMessageDto;
import com.example.WebChat.Entity.PrivateMessage;
import com.example.WebChat.Entity.Users;
import com.example.WebChat.Service.ChatRepoAccess;
import com.example.WebChat.Service.PvtMessageRepoAccess;
import com.example.WebChat.Service.UserRepoAccess;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@AllArgsConstructor
public class MessageCont {

    private static final Logger log = LoggerFactory.getLogger(MessageCont.class);
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final UserRepoAccess userRepo;
    private final ChatRepoAccess chatRepo;
    private final PvtMessageRepoAccess messageRepo;
    private final Dispatcher dispatcher;

    @MessageMapping("/message/{roomid}")
    public void groupMessage(@Payload GroupDto dto, @DestinationVariable String roomid){
        log.info("room_id: {} with message {}", roomid, dto.toString());
        simpMessagingTemplate.convertAndSend("/topic/group/" + roomid, dto);
        dispatcher.dispatch(dto);
    }

    @MessageMapping("/private/message")
    @Transactional
    public void privateMessage(@Payload PrivateMessageDto messageCont, Principal principal){
        String senderName = principal.getName();
        Users sender = userRepo.findByUsername(senderName);
        Users receiver = userRepo.findByUsername(messageCont.getReceiverName());
        PrivateChat chat = chatRepo.findChatByUsers(sender, receiver);
        PrivateMessage message = new PrivateMessage(sender, chat, messageCont.getMessage(),messageCont.getMessageType());
        messageRepo.save(senderName,messageCont.getReceiverName(),message);
        log.info("message is ==>{}",messageCont.getMessage());
        log.info("sendername is ==>{}",senderName);
        simpMessagingTemplate.convertAndSendToUser(
                messageCont.getReceiverName(), "/queue/private", messageCont );
    }
}
