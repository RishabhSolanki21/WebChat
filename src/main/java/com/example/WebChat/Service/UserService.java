//package com.example.WebChat.Service;
//
//import com.example.WebChat.Entity.Users;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Service;
//
//import java.util.Collection;
//import java.util.List;
//
//public class UserService implements UserDetails {
//
//    private final Users users;
//    public UserService(Users users) {
//        this.users = users;
//    }
//
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return List.of(null);
//    }
//
//    @Override
//    public String getPassword() {
//        return users.getPassword();
//    }
//
//    @Override
//    public String getUsername() {
//        return users.getUsername();
//    }
//
//}
