package com.example.WebChat.Controller;


import com.example.WebChat.Entity.Messagesof;
import com.example.WebChat.Entity.Users;
import com.example.WebChat.Repository.UserRepo;
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
    @MessageMapping("/message/{roomid}")
    public String groupmessage(@Payload Users messageCont, @DestinationVariable String roomid) {
        System.out.println(messageCont.getContent());
        simpMessagingTemplate.convertAndSend("/topic/group/" + roomid, messageCont);
        System.out.println(messageCont.getContent());
        userRepo.save(messageCont);
        return messageCont.getContent();
    }
//    @Header("simpSessionAttributes") Map<String, Object> sessionAttributes,
@MessageMapping("/private/message")
public void privatemessage(@Payload Messagesof messageCont) {
//    String actualSender = principal.getName();
//    messageCont.setSendername(actualSender);

    System.out.println("=== SENDING MESSAGE ===");
    System.out.println("From: " + messageCont.getSendername());
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
