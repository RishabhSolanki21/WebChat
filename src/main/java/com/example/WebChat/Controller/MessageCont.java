package com.example.WebChat.Controller;


import com.example.WebChat.Entity.Chat;
import com.example.WebChat.Entity.Message;
import com.example.WebChat.Dto.MessageDto;
import com.example.WebChat.Entity.Users;
import com.example.WebChat.Repository.MessageRepo;
import com.example.WebChat.Repository.UserRepo;
import com.example.WebChat.Repository.chatRepo;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import java.security.Principal;

@Controller
public class MessageCont {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private chatRepo chatRepo;
    @Autowired
    private MessageRepo messageRepo;

    @Autowired
    private ModelMapper modelMapper;

    @MessageMapping("/message/{roomid}")
    public void groupmessage(@Payload Users messageCont, @DestinationVariable String roomid) {
//        System.out.println(messageCont.getContent());
        simpMessagingTemplate.convertAndSend("/topic/group/" + roomid, messageCont);
//        System.out.println(messageCont.getContent());
    }

@MessageMapping("/private/message")
@Transactional
public void privatemessage(@Payload MessageDto messageCont, Principal principal) {
    String senderName = principal.getName();
    Users sender=userRepo.findByUsername(senderName);
    Users receiver=userRepo.findByUsername(messageCont.getReceivername());
    Chat chat=chatRepo.findChatByUsers(sender,receiver)
            .orElseGet(()->chatRepo.save(new Chat(sender,receiver)));
    Message message=new Message(sender,chat,messageCont.getMessage());
    messageRepo.save(message);
    System.out.println("=== SENDING MESSAGE ===");
    System.out.println("To: " + messageCont.getReceivername());
    System.out.println("Message: " + messageCont.getMessage());
    System.out.println("Destination: /user/" + messageCont.getReceivername() + "/private");
    System.out.println("Message object: " + messageCont);

    simpMessagingTemplate.convertAndSendToUser(
            messageCont.getReceivername(),
            "/private",
            messageCont
    );
    System.out.println("✅ Message sent!");
}
}
