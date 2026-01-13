package com.example.WebChat.Controller;


import com.example.WebChat.Entity.Users;
import com.example.WebChat.Repository.UserRepo;
import com.example.WebChat.Security.CreateJwt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

@RestController
public class RestC {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CreateJwt createJwt;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/login")
    public String addUser(@RequestBody Users user) {
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
    @GetMapping("/get/{name}")
    public ResponseEntity<Boolean> getUser(@PathVariable String name) {
        return ResponseEntity.ok(userRepo.existsByUsername(name))       ;
    }

    @PostMapping("/af")
    public String getUserById(@RequestBody String fname,Principal principal) {
        System.out.println("friend name===>"+fname);
        System.out.println(principal.getName());
        Users user =userRepo.findByUsername(principal.getName());
        user.setFriendsname(fname);
        userRepo.save(user);
        return "friend is saved in db with name " + fname;
    }
}
