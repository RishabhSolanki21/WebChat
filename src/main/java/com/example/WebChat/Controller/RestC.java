package com.example.WebChat.Controller;

import com.example.WebChat.Configurations.ModelMapperConfig;
import com.example.WebChat.CustomException.BadCredential;
import com.example.WebChat.Dto.PrivateChatDto;
import com.example.WebChat.Entity.PrivateChat;
import com.example.WebChat.Entity.Users;
import com.example.WebChat.Repository.PvtMessageRepo;
import com.example.WebChat.Repository.UserRepo;
import com.example.WebChat.Security.CreateJwt;
import com.example.WebChat.Service.ChatRepoAccess;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
public class RestC {

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private PvtMessageRepo messageRepo;

    @Autowired
    private CreateJwt createJwt;
    @Autowired
    private ModelMapperConfig modelMapper;

    @Autowired
    private ChatRepoAccess chat;

    @Autowired
    private static final Logger logger= LogManager.getLogger(RestC.class);
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public String Register(@RequestBody Users user) {
        System.out.println(user.getUsername()+"==>"+user.getPassword());
        Users user1 = new Users();
        user1.setUsername(user.getUsername());
        user1.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepo.save(user1);
        String token=createJwt.createJwt(user1.getUsername());
        System.out.println("hii there on login");
        System.out.println(token);
        return token;
    }
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Users user){
        System.out.println(user.getUsername()+"==>"+user.getPassword());
        try{
            Authentication authentication=authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            user.getUsername(),user.getPassword()
                    )
            );
            logger.info("hii there on login {}",authentication.getName());
            String token=createJwt.createJwt(authentication.getName());
            return ResponseEntity.ok().body(token);
        }catch (BadCredentialsException e){
            throw new BadCredential("Bad credentials");
        }
    }
    @GetMapping("/get/{name}")
    public ResponseEntity<Boolean> getUser(@PathVariable String name) {
        return ResponseEntity.ok(userRepo.existsByUsername(name))       ;
    }

    @GetMapping("/af")
    public ResponseEntity<List<PrivateChatDto>> getUserById(Principal principal) {
        String username = principal.getName();
        System.out.println("getting a friends list from db===>"+username);
        Users user = userRepo.findByUsername(username);
        List<PrivateChat> chat1= chat.findChatByUsers1OrUsers2(user, user);
        List<PrivateChatDto> chatDtoList=modelMapper.modelToDto(chat1,user,messageRepo);
        System.out.println("Loop started");
        for (PrivateChatDto chatDto : chatDtoList) {
            System.out.println(chatDto.toString());
        }
        return ResponseEntity.ok().body(chatDtoList);
    }
}
