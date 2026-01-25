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

    public List<ChatDto> modelToDto(List<Chat> chat, Users currentUser,  MessageRepo messageRepo) {
        List<ChatDto> chatDtos = new ArrayList<>();
        for (Chat chat1 : chat) {
            Users friend= chat1.getUsers1().equals(currentUser)?chat1.getUsers2():chat1.getUsers1();
            List<MessageDto> messageList=messageRepo.findByChat_ChatId(chat1.getChatId())
                    .stream().map(
                    m -> {
                        String sender=m.getSender().getUsername();
                        String receiver=m.getSender().getUsername().equals(friend.getUsername())?
                               currentUser.getUsername(): friend.getUsername();
                        return new MessageDto(
                                sender, m.getContent(),receiver, m.getTime()
                        );
                    })
                    .collect(Collectors.toList());
           chatDtos.add(new ChatDto(friend.getUsername(),messageList));
        }
        return chatDtos;
    }
}
