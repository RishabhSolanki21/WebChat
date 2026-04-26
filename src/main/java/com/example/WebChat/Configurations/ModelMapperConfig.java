package com.example.WebChat.Configurations;

import com.example.WebChat.Dto.PrivateChatDto;
import com.example.WebChat.Dto.PrivateMessageDto;
import com.example.WebChat.Entity.PrivateChat;
import com.example.WebChat.Entity.PrivateMessage;
import com.example.WebChat.Entity.Users;
import com.example.WebChat.Service.ChatRepoAccess;
import com.example.WebChat.Service.MessageRepoAccess;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Configuration
@AllArgsConstructor
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    private final MessageRepoAccess messageRepoAccess;
    private final ChatRepoAccess chatRepoAccess;

    public List<PrivateChatDto> modelToDto(List<PrivateChat> chat, Users currentUser,Integer ps,Integer pn) {
        List<PrivateChatDto> chatDtos = new ArrayList<>();
        for (PrivateChat chat1 : chat) {
            Users friend= chat1.getUsers1().equals(currentUser)?chat1.getUsers2():chat1.getUsers1();
            List<PrivateMessageDto> messageList=messageRepoAccess.findByChat_ChatId(chat1.getChatId(),ps,null,pn)
                    .stream().map(
                    m -> {
                        String sender=m.getSender().getUsername();
                        String receiver=m.getSender().getUsername().equals(friend.getUsername())?
                               currentUser.getUsername(): friend.getUsername();
                        return new PrivateMessageDto(
                                m.getMessageId(),sender, m.getContent(),receiver,true,m.getType(),m.getTime()
                        );
                    })
                    .collect(toList());//return mutable list
           chatDtos.add(new PrivateChatDto(friend.getUsername(),chat1.getChatId(),messageList));
        }
        return chatDtos;
    }

    public List<PrivateMessageDto> modelMapper(Long chatId, Integer ps, Long cursor, Integer pn) {
        List<PrivateMessage> message=messageRepoAccess.findByChat_ChatId(chatId,ps,cursor,pn);
        boolean hasNext;
        hasNext= message.size() >= ps;
        return message.stream().map(
                    m-> {
                        PrivateChat privateChat = chatRepoAccess.findChatById(chatId);
                        String sendername=m.getSender().getUsername();
                        String receivername=privateChat.getUsers1().getUsername().equals(sendername)?
                                privateChat.getUsers2().getUsername(): privateChat.getUsers1().getUsername();
                       return new PrivateMessageDto(m.getMessageId(), sendername, m.getContent(), receivername, hasNext,m.getType(),m.getTime());
                    }).toList();// return immutable list
    }

}
