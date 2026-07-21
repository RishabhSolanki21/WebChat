package com.example.WebChat.Controller;


import com.example.WebChat.Dto.OnlineUsers;
import com.example.WebChat.Dto.States;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Controller
public class EventListner {

    private ConcurrentHashMap<String, Set<OnlineUsers>>map=new ConcurrentHashMap<>();
    Set<OnlineUsers> onlineUsersSet;
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
            OnlineUsers onlineUsers=new OnlineUsers(username, States.SUBSCRIBE,room,accessor.getSessionId());
            map.computeIfAbsent(room,set->
                onlineUsersSet = new HashSet<>()).add(onlineUsers);
            log.info("Session accessor event2 {}",username);
            log.info("Online Users {}",map);
            template.convertAndSend("/topic/group/"+room, map.get(room));
        }
    }

    @MessageMapping("/unsubscribe")
    public void UnsubscribeEvent(@Payload OnlineUsers onlineUsers, Message<?>message) {
        log.info("Online Users leaving {}",onlineUsers);
        StompHeaderAccessor accessor=StompHeaderAccessor.wrap(message);
        String session=accessor.getSessionId();
        log.info("User leaving session {}",session);
        log.info("Session leaving {}",session);
        template.convertAndSend("/topic/group/"+onlineUsers.getRoomId(),
                Objects.requireNonNull(map.computeIfPresent(onlineUsers.getRoomId(), (roomId, set) -> {
            set.removeIf(onlineUsers1 -> onlineUsers1.getSessionId().equals(session));
            return map.isEmpty()?null:map.get(roomId);
        })));
    }
}
