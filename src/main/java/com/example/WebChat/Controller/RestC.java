//package com.example.WebChat.Controller;
//
//
//import com.example.WebChat.Entity.Users;
//import com.example.WebChat.Repository.UserRepo;
//import com.example.WebChat.Security.CreateJwt;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.Optional;
//
//@RestController
//public class RestC {
//
//    @Autowired
//    private UserRepo userRepo;
//
//    @Autowired
//    private CreateJwt createJwt;
//
//    @Autowired
//    private BCryptPasswordEncoder bCryptPasswordEncoder;
//
//    @PostMapping("/login")
//    public String addUser(@RequestBody Users user) {
//        System.out.println(user.getUsername()+"==>"+user.getPassword());
//        Users user1 = new Users.Builder().setUsername(user.getUsername())
//                .setPassword(bCryptPasswordEncoder.encode(user.getPassword())).build();
//        userRepo.save(user1);
//        String token=createJwt.createJwt(user1);
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        System.out.println("hii there login before");
//        System.out.println(token+"==>"+auth.getPrincipal());
//        System.out.println(token);
//        return token;
//    }
//    @GetMapping("/get/{name}")
//    public ResponseEntity<Boolean> getUser(@PathVariable String name) {
//        return ResponseEntity.ok(userRepo.existsByUsername(name))       ;
//    }
//
//    @GetMapping("/get")
//    public Optional<Users> getUserById(@RequestParam("id") Long id) {
//        return userRepo.findById(id);
//    }
//    @GetMapping("/getFriend")
//    public Users getFriends(@RequestParam("name") String name) {
//        Optional<Users> user=userRepo.findById(id);
//       String name= user.get().getFriendName(id);
//      return userRepo.findByUsername(name);
//    }
//}
