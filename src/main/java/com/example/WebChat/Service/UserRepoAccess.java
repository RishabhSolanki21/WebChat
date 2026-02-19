package com.example.WebChat.Service;


import com.example.WebChat.Entity.Users;
import com.example.WebChat.Repository.UserRepo;
import org.apache.catalina.User;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

@Service
//@EnableAsync
public class UserRepoAccess {

    private final UserRepo userRepo;
    public UserRepoAccess(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public void save(Users user) {
        userRepo.save(user);
    }
    @Async
    public Users findByUsername(String username)throws InterruptedException {
        Thread.sleep(5000);
        return userRepo.findByUsername(username);
    }
}
