package com.example.WebChat.Service;

import com.example.WebChat.Dto.GroupDto;
import com.example.WebChat.Entity.GroupChat;
import com.example.WebChat.Entity.Project_data;
import com.example.WebChat.Entity.Users;
import com.example.WebChat.Repository.GroupChatRepo;
import com.example.WebChat.Repository.ProjectRepo;
import com.example.WebChat.Repository.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class ChatHandler {

    private final GroupChatRepo groupChatRepo;
    private final UserRepo userRepo;
    private final ProjectRepo projectRepo;


    public void save(GroupDto groupDto){
        Users users=userRepo.findByUsername(groupDto.getUsername());
        Project_data data=projectRepo.findById(groupDto.getProject_id()).orElseGet(()->projectRepo.save(
                Project_data.builder().Project_id(groupDto.getProject_id())
                        .creator(users).build()));
        GroupChat groupChat = GroupChat.builder()
                .users(users).projectData(data)
                .message(groupDto.getMessage()).created_at(LocalDateTime.now())
                .build();
        groupChatRepo.save(groupChat);
    }
}
