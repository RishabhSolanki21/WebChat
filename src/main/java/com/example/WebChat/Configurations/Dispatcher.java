package com.example.WebChat.Configurations;

import com.example.WebChat.Dto.GroupDto;
import com.example.WebChat.Dto.MessageType;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class Dispatcher {

    Map<MessageType,Handler>dispatcher = new HashMap<MessageType,Handler>();

    Dispatcher(List<Handler> handlers) {
        for (Handler handler : handlers) {
            dispatcher.put(handler.messageType(),handler);
        }
    }
    public void dispatch(GroupDto groupDto) {
        dispatcher.get(groupDto.getType()).save(groupDto);
    }
}
