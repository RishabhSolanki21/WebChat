package com.example.WebChat.Service;

import com.example.WebChat.Configurations.Handler;
import com.example.WebChat.Dto.GroupDto;
import com.example.WebChat.Entity.Project_data;
import com.example.WebChat.Entity.Users;
import com.example.WebChat.Repository.ProjectRepo;
import com.example.WebChat.Repository.UserRepo;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.time.LocalDateTime;

@Slf4j
@Service
@AllArgsConstructor
public class ProjectHandler {

    private final ProjectRepo projectRepo;
    private final UserRepo userRepo;

//    public Project_data update(GroupDto groupDto) {
//        Project_data data=projectRepo.findById(groupDto.getProject_id()).orElseGet(()->projectRepo.save(
//                Project_data.builder().Project_id(groupDto.getProject_id()).creator(
//                        userRepo.findByUsername(groupDto.getUsername())).build()));
//        log.info("current project data {}", data);
//        data.setDocument(groupDto.getMessage());
//        data.setLast_updater(userRepo.findByUsername(groupDto.getUsername()));
//        data.setDate(LocalDateTime.now());
//        return projectRepo.save(data);
//    }
}
