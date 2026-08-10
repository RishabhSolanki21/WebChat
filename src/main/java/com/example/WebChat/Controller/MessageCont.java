package com.example.WebChat.Controller;

import com.example.WebChat.Dto.*;
import com.example.WebChat.Entity.PrivateChat;
import com.example.WebChat.Entity.PrivateMessage;
import com.example.WebChat.Entity.Users;
import com.example.WebChat.Service.ChatHandler;
import com.example.WebChat.Service.ChatRepoAccess;
import com.example.WebChat.Service.PvtMessageRepoAccess;
import com.example.WebChat.Service.UserRepoAccess;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.awt.*;
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
    private final ConcurrentHashMap<String,DocsVersion>version=new ConcurrentHashMap<>();
    private final ObjectMapper objectMapper;

    @MessageMapping("/message/{roomid}")
    public void groupMessage(@Payload RoomEvent roomEvent , @DestinationVariable String roomid) throws InterruptedException, JsonProcessingException {
        log.info("room_id: {} with message {}", roomid, roomEvent.toString());
//        if(dto.getUsername().equals("rishabh")){
//            Thread.sleep(10000);
//        }
        ChangedText text = objectMapper.treeToValue(roomEvent.getPayload(), ChangedText.class);
        if (roomEvent.getType() == MessageType.PASS) {
            int v = text.getVersion();
            version.computeIfPresent(roomid, (room, docsVersion) -> {
                log.info("received text {}",text);
                StringBuilder updatedText = new StringBuilder(docsVersion.getDocs());
                log.info("updated text0 {}",updatedText);
                updatedText.delete(text.getStart(),text.getStart()+text.getDelete_count());
                log.info("updated text {}",updatedText);
                updatedText.insert(text.getStart(), text.getNewText());
                log.info("updated text2 {}",updatedText);
                docsVersion.setDocs(String.valueOf(updatedText));
                docsVersion.setVersion(docsVersion.getVersion() + 1);
                return docsVersion;
            });
            version.computeIfAbsent(roomid, room ->
                    new DocsVersion(v, text.getNewText())
            );
        }
        simpMessagingTemplate.convertAndSend("/topic/group/" + roomid, roomEvent);
//        if (dto.getType()== MessageType.CHAT){
//            chatHandler.save(dto);
//        }
    }

    @MessageMapping("/caret/{roomId}")
    public void CaretUpdate(@Payload CaretPosition caretPosition,@DestinationVariable String roomId,Principal principal){
        String username = principal.getName();
        log.info("not updated map value {}",map.get(roomId));
        map.get(roomId).get(username).setCaretPosition(caretPosition);
        log.info("Caret Position {}",caretPosition);
        log.info("updated map value {}",map.get(roomId));
        simpMessagingTemplate.convertAndSend("/topic/group/" + roomId,map.get(roomId));
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
