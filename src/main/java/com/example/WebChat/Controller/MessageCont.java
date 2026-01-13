package com.example.WebChat.Controller;


import com.example.WebChat.Configurations.ModelMapperConfig;
import com.example.WebChat.Entity.Messagesof;
import com.example.WebChat.Entity.Users;
import com.example.WebChat.Repository.UserRepo;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import java.security.Principal;

@Controller
public class MessageCont {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;

    @MessageMapping("/message/{roomid}")
    public void groupmessage(@Payload Users messageCont, @DestinationVariable String roomid) {
        System.out.println(messageCont.getContent());
        simpMessagingTemplate.convertAndSend("/topic/group/" + roomid, messageCont);
        System.out.println(messageCont.getContent());
    }

@MessageMapping("/private/message")
@Transactional
public void privatemessage(@Payload Messagesof messageCont,Principal principal) {
    String actualSender = principal.getName();
    Users users=userRepo.findByUsername(actualSender);
    users.getContent().add(messageCont.getMessage());
    userRepo.save(users);
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
