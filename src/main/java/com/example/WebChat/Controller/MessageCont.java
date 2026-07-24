package com.example.WebChat.Controller;

import com.example.WebChat.Dto.*;
import com.example.WebChat.Entity.PrivateChat;
import com.example.WebChat.Entity.PrivateMessage;
import com.example.WebChat.Entity.Users;
import com.example.WebChat.Service.ChatHandler;
import com.example.WebChat.Service.ChatRepoAccess;
import com.example.WebChat.Service.PvtMessageRepoAccess;
import com.example.WebChat.Service.UserRepoAccess;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

import java.security.Principal;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Controller
@AllArgsConstructor
public class MessageCont {

    private static final Logger log = LoggerFactory.getLogger(MessageCont.class);
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final UserRepoAccess userRepo;
    private final ChatRepoAccess chatRepo;
    private final PvtMessageRepoAccess messageRepo;
    private final ChatHandler chatHandler;
    private final ConcurrentHashMap<String, ConcurrentHashMap<String,OnlineUsers>> map=new ConcurrentHashMap<>();
    private final SimpMessagingTemplate template;

    @MessageMapping("/message/{roomid}")
    public void groupMessage(@Payload GroupDto dto, @DestinationVariable String roomid){
        log.info("room_id: {} with message {}", roomid, dto.toString());
        simpMessagingTemplate.convertAndSend("/topic/group/" + roomid, dto);
        if (dto.getType()== MessageType.CHAT){
            chatHandler.save(dto);
        }
    }

    @EventListener
    public void SubscribeEvent(SessionSubscribeEvent event) {
        StompHeaderAccessor accessor=StompHeaderAccessor.wrap(event.getMessage());

        String Destination=accessor.getDestination();
        assert Destination != null;
        String room=Destination.substring("/topic/group/".length());
        log.info("Session subscribe event {} {}",event,room);
        log.info("Session accessor event {}",accessor.getDestination());
        if (Objects.requireNonNull(accessor.getDestination()).startsWith("/topic/group/")) {
            String username=Objects.requireNonNull(accessor.getUser().getName());
            CaretPosition caretPosition=new CaretPosition(0,0);
            OnlineUsers onlineUsers=new OnlineUsers(username, States.SUBSCRIBE,room,accessor.getSessionId(), MessageType.TRACKING,caretPosition);
            map.computeIfAbsent(room, roomid->
                    new ConcurrentHashMap<>()).put(username,onlineUsers);
            log.info("Session accessor event2 {}",username);
            log.info("Online Users {}",map);
            template.convertAndSend("/topic/group/"+room, map.get(room));
        }
    }

    @MessageMapping("/caret/{roomId}")
    public void CaretUpdate(@Payload CaretPosition caretPosition,@DestinationVariable String roomId,Principal principal){
        String username = principal.getName();
        map.get(roomId).get(username).setCaretPosition(caretPosition);
        log.info("Caret Position {}",caretPosition);
        simpMessagingTemplate.convertAndSend("/topic/group/" + roomId,map.get(roomId));
    }

    @MessageMapping("/unsubscribe")
    public void UnsubscribeEvent(@Payload OnlineUsers onlineUsers, Message<?> message) {
        log.info("Online Users leaving {}",onlineUsers);
        StompHeaderAccessor accessor=StompHeaderAccessor.wrap(message);
        String session=accessor.getSessionId();
        log.info("User leaving session {}",session);
        log.info("Session leaving {}",session);
        template.convertAndSend("/topic/group/"+onlineUsers.getRoomId(),
                Objects.requireNonNull(map.computeIfPresent(onlineUsers.getRoomId(),
                        (roomId, usermap) -> {
                    usermap.remove(onlineUsers.getUsername());
                    return usermap;
                })));
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
