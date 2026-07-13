package com.example.WebChat.Configurations;

import com.example.WebChat.Dto.GroupDto;
import com.example.WebChat.Entity.MessageType;
import org.springframework.stereotype.Component;

@Component
public interface Handler {

    MessageType messageType();
    void save(GroupDto groupDto);
}
