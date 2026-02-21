package com.example.WebChat.Service;

import com.example.WebChat.Entity.Users;
import com.example.WebChat.Repository.UserRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDtails implements UserDetailsService {

    private static final Logger log = LoggerFactory.getLogger(MyUserDtails.class);

    private final UserRepoAccess userRepo;
    MyUserDtails(UserRepoAccess userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        log.info("loadUserByUsername {}",username);
        Users user = userRepo.findByUsername(username);
        if (user==null) {
            throw new UsernameNotFoundException(username);
        }
        return new UserService(user);
    }
}
