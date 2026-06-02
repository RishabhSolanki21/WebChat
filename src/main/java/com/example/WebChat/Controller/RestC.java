package com.example.WebChat.Controller;

import com.example.WebChat.Configurations.ModelMapperConfig;
import com.example.WebChat.CustomException.BadCredential;
import com.example.WebChat.Dto.FileDto;
import com.example.WebChat.Dto.PrivateChatDto;
import com.example.WebChat.Entity.PrivateChat;
import com.example.WebChat.Entity.Users;
import com.example.WebChat.Repository.UserRepo;
import com.example.WebChat.Security.CreateJwt;
import com.example.WebChat.Security.FileHandling;
import com.example.WebChat.Service.ChatRepoAccess;
import com.example.WebChat.Service.CycleService;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.security.Principal;
import java.util.Collections;
import java.util.List;

@RestController
@AllArgsConstructor
public class RestC {

    private final UserRepo userRepo;

    private final CreateJwt createJwt;
    private final ModelMapperConfig modelMapper;
    private final ChatRepoAccess chat;

    private static final Logger logger= LogManager.getLogger(RestC.class);
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AuthenticationManager authenticationManager;
    private final WebClient client = WebClient.create();
    private final FileHandling fileHandling;
    private final CycleService cycleService;

    @PostMapping("/register")
    public String Register(@RequestBody Users user) {
        logger.info("{}==>{}",user.getPassword(),user.getUsername());
        Users user1 = new Users();
        user1.setUsername(user.getUsername());
        user1.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepo.save(user1);
        String token=createJwt.createJwt(user1.getUsername());
        logger.info("hii there on registration");
        logger.info(token);
        return token;
    }
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Users user){
        logger.info("{}==> {}",user.getUsername(),user.getPassword());
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
    public ResponseEntity<?> getUserById(Principal principal,
                                         @RequestParam(name="chatId",required = false) Long chatId,
                                         @RequestParam(name = "selectedF",required = false)String friendName,
                                         @RequestParam(required = false) Long cursor,
                                         @RequestParam(required = false, defaultValue = "10") Integer ps,
                                         @RequestParam(required = false,defaultValue = "0")Integer pn
    ) {
        if (friendName==null){
            String username = principal.getName();
        logger.info("getting a friends list from db===>{}",username);
//        Users user = userRepo.findByUsername(username);
        List<PrivateChatDto> chatDto=cycleService.findChatByUsers2(username,ps,pn);
//        if (chat1 == null || chat1.isEmpty()) {
//            logger.info("No chats found for user: {}",username);
//            return ResponseEntity.ok(Collections.emptyList());
//        }        List<PrivateChatDto> chatDtoList=modelMapper.modelToDto(chat1,user,ps,pn);
//        List<PrivateChatDto> chatDtoList=modelMapper.modelToDto(chatDto,user,ps,pn);
//        logger.info("Loop started");
//        for (PrivateChatDto chatDto : chatDtoList) {
//            logger.info(chatDto.toString());
//        }
        return ResponseEntity.ok(chatDto);
        }
        else {
            logger.info("getting a friends list from db===>{} {} {} {}",friendName,cursor,ps,pn);
            return ResponseEntity.ok(modelMapper.modelMapper(chatId,ps,cursor,pn));
        }
    }//id content time

    @PostMapping("/upload")
    public FileDto fileUpload(@RequestParam("file") MultipartFile file) throws IOException {
        return fileHandling.filesave(file);
    }

    @GetMapping("/getfile/{filename}")
    public ResponseEntity<UrlResource> getFile(@PathVariable String filename) throws IOException {
        return fileHandling.retriveFile(filename);
    }

    @GetMapping("/extApi")
    public String externalApi(){
       return client.get()
                .uri("www.fuck_off.com")
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
