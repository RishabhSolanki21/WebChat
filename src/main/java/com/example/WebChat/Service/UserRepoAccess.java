package com.example.WebChat.Service;


import com.example.WebChat.Entity.Users;
import com.example.WebChat.Repository.UserRepo;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserRepoAccess {

    private final UserRepo userRepo;
    public UserRepoAccess(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public void save(Users user) {
        userRepo.save(user);
    }

    public Users findByUsername(String username){
        return userRepo.findByUsername(username);
    }
}
