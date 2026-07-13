package com.example.WebChat.Service;

import com.example.WebChat.Configurations.Handler;
import com.example.WebChat.Dto.GroupDto;
import com.example.WebChat.Entity.GroupChat;
import com.example.WebChat.Entity.MessageType;
import com.example.WebChat.Entity.Project_data;
import com.example.WebChat.Entity.Users;
import com.example.WebChat.Repository.GroupChatRepo;
import com.example.WebChat.Repository.ProjectRepo;
import com.example.WebChat.Repository.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class ChatHandler implements Handler {

    private final GroupChatRepo groupChatRepo;
    private final UserRepo userRepo;
    private final ProjectRepo projectRepo;

    @Override
    public MessageType messageType() {
        return MessageType.CHAT;
    }

    @Override
    public void save(GroupDto groupDto){
        Users users=userRepo.findByUsername(groupDto.getUsername());
        Project_data data=projectRepo.findById(groupDto.getProject_id()).orElse(Project_data.builder()
                .Project_id(groupDto.getProject_id()).build());
        GroupChat groupChat = GroupChat.builder()
                .users(users).projectData(data).message(groupDto.getMessage()).created_at(LocalDateTime.now())
                .build();
        groupChatRepo.save(groupChat);
    }
}
