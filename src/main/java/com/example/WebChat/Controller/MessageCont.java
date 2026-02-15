package com.example.WebChat.Controller;

import com.example.WebChat.Configurations.ThreadImpl;
import com.example.WebChat.Dto.GroupDto;
import com.example.WebChat.Entity.PrivateChat;
import com.example.WebChat.Dto.PrivateMessageDto;
import com.example.WebChat.Entity.PrivateMessage;
import com.example.WebChat.Entity.Users;
import com.example.WebChat.Repository.MessageRepo;
import com.example.WebChat.Repository.UserRepo;
import com.example.WebChat.Repository.ChatRepo;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import java.security.Principal;

@Controller
public class MessageCont {

    private static final Logger log = LoggerFactory.getLogger(MessageCont.class);
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private ChatRepo chatRepo;
    @Autowired
    private MessageRepo messageRepo;
    @Autowired
    private ThreadImpl thread;

    @MessageMapping("/message/{roomid}")
    public void groupmessage(@Payload GroupDto dto, @DestinationVariable String roomid) throws InterruptedException {
        log.info(String.valueOf(dto));
//        thread.sleep(2000);
        simpMessagingTemplate.convertAndSend("/topic/group/" + roomid,dto);
    }

@MessageMapping("/private/message")
@Transactional
public void privatemessage(@Payload PrivateMessageDto messageCont, Principal principal) {
        simpMessagingTemplate.convertAndSendToUser(
        messageCont.getReceivername(),
        "/private", messageCont );
    System.out.println("✅ Message sent!");
    String senderName = principal.getName();
    Users sender=userRepo.findByUsername(senderName);
    Users receiver=userRepo.findByUsername(messageCont.getReceivername());
    PrivateChat chat=chatRepo.findChatByUsers(sender,receiver)
            .orElseGet(()->chatRepo.save(new PrivateChat(sender,receiver)));
    PrivateMessage message=new PrivateMessage(sender,chat,messageCont.getMessage());
    messageRepo.save(message);
    System.out.println("=== SENDING MESSAGE ===");
    System.out.println("To: " + messageCont.getReceivername());
    System.out.println("Message: " + messageCont.getMessage());
    System.out.println("Destination: /user/" + messageCont.getReceivername() + "/private");
    System.out.println("Message object: " + messageCont);
    }
}
