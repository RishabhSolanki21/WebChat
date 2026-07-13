package com.example.WebChat.Service;

import com.example.WebChat.Configurations.ModelMapperConfig;
import com.example.WebChat.Dto.PrivateChatDto;
import com.example.WebChat.Entity.PrivateChat;
import com.example.WebChat.Entity.Users;
import com.example.WebChat.Repository.ChatRepo;
import com.example.WebChat.Repository.UserRepo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CycleService {
    private final UserRepo userRepo;
    private final ChatRepo chatRepo;
    private final Logger log = LoggerFactory.getLogger(CycleService.class);
    private final ModelMapperConfig modelMapper;

    @Cacheable(
                value = "friends",
            key = "#username"
    )
    public List<PrivateChatDto> findChatByUsers2(String username, Integer ps, Integer pn) {
        Users user = userRepo.findByUsername(username);
        log.info("DB hit with findChatByUsers2: {}",user);
        List<PrivateChat> chat1= chatRepo.findChatByUsers1OrUsers2(user, user);
        log.info("DB hit with findChatByUsers2: and completed");
        List<PrivateChatDto> chatDtoList=modelMapper.modelToDto(chat1,user,ps,pn);
        return chatDtoList;
    }
}
