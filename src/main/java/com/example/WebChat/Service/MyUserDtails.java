package com.example.WebChat.Service;

import com.example.WebChat.Entity.Users;
import com.example.WebChat.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDtails implements UserDetailsService {

    private final UserRepoAccess userRepo;
    MyUserDtails(UserRepoAccess userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        System.out.println("loadUserByUsername " + username);
        Users user = userRepo.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("invalid username or password");
        }
        return new UserService(user);
    }
}
