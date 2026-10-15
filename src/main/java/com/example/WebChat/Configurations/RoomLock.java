package com.example.WebChat.Configurations;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

@Component
@AllArgsConstructor
public class RoomLock {

    private final ConcurrentHashMap<String,Object>lock=new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, Object> caretLocks = new ConcurrentHashMap<>();

    public Object roomlock(String roomId){
        return lock.computeIfAbsent(roomId,k->new Object());
    }

    public Object caretLock(String roomId) {
        return caretLocks.computeIfAbsent(roomId, r -> new Object());
    }
}
