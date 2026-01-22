package com.example.WebChat.Controller;


import com.example.WebChat.Entity.Chat;
import com.example.WebChat.Entity.Users;
import com.example.WebChat.Repository.UserRepo;
import com.example.WebChat.Repository.chatRepo;
import com.example.WebChat.Security.CreateJwt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
public class RestC {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CreateJwt createJwt;

    @Autowired
    private chatRepo chat;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/register")
    public String Register(@RequestBody Users user) {
        System.out.println(user.getUsername()+"==>"+user.getPassword());
        Users user1 = new Users();
        user1.setUsername(user.getUsername());
        user1.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepo.save(user1);
        String token=createJwt.createJwt(user1);
        System.out.println("hii there on login");
        System.out.println(token);
        return token;
    }
    @PostMapping("/login")
    public String login(@RequestBody Users user){
        System.out.println(user.getUsername()+"==>"+user.getPassword());
        Optional<Users> user1=Optional.of(userRepo.findByUsername(user.getUsername()));
        if (bCryptPasswordEncoder.matches(user.getPassword(),user1.get().getPassword())){
            return createJwt.createJwt(user);
        }
        return "no such user exists";
    }
    @GetMapping("/get/{name}")
    public ResponseEntity<Boolean> getUser(@PathVariable String name) {
        return ResponseEntity.ok(userRepo.existsByUsername(name))       ;
    }

    @GetMapping("/af")
    public Optional<List<Chat>> getUserById(Principal principal) {
        String username = principal.getName();
        System.out.println("getting a friends list from db===>"+username);
        Users user = userRepo.findByUsername(username);
        Optional<List<Chat>> chat1= Optional.of(chat.findChatByUsers1OrUsers2(user, user).stream().toList());
        System.out.println("chat1====>"+chat1);
        return chat1;
    }
}
