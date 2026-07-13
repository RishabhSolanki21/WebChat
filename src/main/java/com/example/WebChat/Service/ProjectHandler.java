package com.example.WebChat.Service;

import com.example.WebChat.Configurations.Handler;
import com.example.WebChat.Dto.GroupDto;
import com.example.WebChat.Entity.MessageType;
import com.example.WebChat.Entity.Project_data;
import com.example.WebChat.Entity.Users;
import com.example.WebChat.Repository.ProjectRepo;
import com.example.WebChat.Repository.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class ProjectHandler implements Handler {

    private final ProjectRepo projectRepo;
    private final UserRepo userRepo;

    @Override
    public MessageType messageType() {
        return MessageType.PROJECT;
    }

    @Override
    public void save(GroupDto groupDto) {
        Users users=userRepo.findByUsername(groupDto.getUsername());
        Project_data data=Project_data.builder()
                .Project_id(groupDto.getProject_id()).document(groupDto.getMessage())
                .creator(users).date(LocalDateTime.now())
                .build();
    }
}
