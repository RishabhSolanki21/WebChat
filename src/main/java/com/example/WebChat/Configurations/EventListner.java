package com.example.WebChat.Configurations;


import com.example.WebChat.Dto.GroupDto;
import com.example.WebChat.Dto.OnlineUsers;
import com.example.WebChat.Dto.States;
import com.example.WebChat.Entity.Users;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;
import org.springframework.web.socket.messaging.SessionUnsubscribeEvent;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Configuration
public class EventListner {

    private ConcurrentHashMap<String, Set<OnlineUsers>>map=new ConcurrentHashMap<>();
    Set<OnlineUsers> onlineUsersSet=new HashSet<>();
    private final SimpMessagingTemplate template;

    public EventListner(SimpMessagingTemplate template) {
        this.template = template;
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
            OnlineUsers onlineUsers=new OnlineUsers(username, States.SUBSCRIBE,room);
            onlineUsersSet.add(onlineUsers);
            map.put(room,onlineUsersSet);
            log.info("Session accessor event2 {}",username);
            log.info("Online Users {}",map);
            template.convertAndSend("/topic/group/"+room, map.get(room));
        }
    }

    @MessageMapping("/unsubscribe")
    public void UnsubscribeEvent(SessionUnsubscribeEvent event) {

        template.convertAndSend("/topic/group/"+1, map.get(1));
    }
}
