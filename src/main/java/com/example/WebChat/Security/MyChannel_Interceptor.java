package com.example.WebChat.Security;

//import com.example.WebChat.Service.JwtClaims;
import com.example.WebChat.Service.JwtClaims;
import com.example.WebChat.Service.MyUserDtails;
import com.example.WebChat.Service.UserService;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageBuilder;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.security.Principal;
import java.util.List;


@Component
public class MyChannel_Interceptor implements ChannelInterceptor {

    @Autowired
    private JwtClaims jwtClaims;

    @Autowired
    private MyUserDtails details;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message,StompHeaderAccessor.class);

        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
            String token=accessor.getFirstNativeHeader("Authorization");
            if (token!=null&&token.startsWith("Bearer ")) {
                token=token.substring(7);
                if(jwtClaims.isValid(token)) {
                    String username=jwtClaims.getUsername(token);
                    UserDetails det=details.loadUserByUsername(username);
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            det,token,det.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
                accessor.setUser(authentication);
                }
            }
            else {
                System.out.println("token is null");
            }
        }
        else {
            System.out.println("==="+accessor.getCommand()+"===");
            System.out.println("==="+accessor.getUser());
            System.out.println(accessor.getUser().getName());

        }
        return MessageBuilder.createMessage(
                message.getPayload(),
                accessor.getMessageHeaders()
        );
    }
}
