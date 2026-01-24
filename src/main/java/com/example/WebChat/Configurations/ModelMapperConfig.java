package com.example.WebChat.Configurations;

import com.example.WebChat.Dto.ChatDto;
import com.example.WebChat.Dto.MessageDto;
import com.example.WebChat.Entity.Chat;
import com.example.WebChat.Entity.Message;
import com.example.WebChat.Entity.Users;
import com.example.WebChat.Repository.MessageRepo;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    public List<ChatDto> modelToDto(List<Chat> chat, Users users,  MessageRepo messageRepo) {
        List<ChatDto> chatDtos = new ArrayList<>();
        for (Chat chat1 : chat) {
            Users user= chat1.getUsers1().equals(users)?chat1.getUsers2():chat1.getUsers1();
            List<MessageDto> messageList=messageRepo.findByChat_ChatId(chat1.getChatId())
                    .stream().map(
                    m -> new MessageDto(
                            m.getSender().getUsername(),m.getContent()
                    ))
                    .collect(Collectors.toList());
           chatDtos.add(new ChatDto(user.getUsername(),messageList));
        }
        return chatDtos;
    }
}
