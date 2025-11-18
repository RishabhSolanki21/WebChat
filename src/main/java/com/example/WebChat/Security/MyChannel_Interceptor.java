package com.example.WebChat.Security;

//import com.example.WebChat.Service.JwtClaims;
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
import org.springframework.stereotype.Component;

import java.security.Principal;
import java.util.List;


@Component
public class MyChannel_Interceptor implements ChannelInterceptor {

//    @Autowired
//    private JwtClaims jwtClaims;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message,StompHeaderAccessor.class);

        if (StompCommand.CONNECT.equals(accessor.getCommand())) {

            String username =  accessor.getFirstNativeHeader("username");

            if (username != null) {
                accessor.setUser(() -> username);
                System.out.println("Connected: user=" + username);
            } else {
                System.out.println("Username is NULL in CONNECT!");
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

////        if (accessor.getUser() == null) {
//            // Try to get JWT from CONNECT frame or session attributes
//            if (StompCommand.CONNECT.equals(accessor.getCommand())) {
//                System.out.println("User is trying to connect... ");
////                List<String> auth = accessor.getNativeHeader("Authorization");
////                if (auth != null && !auth.isEmpty()) {
////                    String token = auth.get(0).replace("Bearer ", "");
////                    if (jwtClaims.isValid(token)) {
////                        String username = jwtClaims.getUsername(token);
////                        UsernamePasswordAuthenticationToken principal =
////                                new UsernamePasswordAuthenticationToken(username, null,
////                                        Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")));
////                        accessor.setUser(principal);
////                        System.out.println("principal from interceptor: " + principal);
////                        accessor.getSessionAttributes().put("principal", principal);
////                        System.out.println("Principal set at CONNECT: " + username);
////                    }
////                }
//            }
//            else {
//                // For SUBSCRIBE / SEND / MESSAGE frames: reuse Principal from session
//                if (accessor.getUser() == null) {
//                    Object principal = accessor.getSessionAttributes().get("principal");
//                    if (principal != null) {
//                        accessor.setUser((Principal) principal);
//                    }
//                }
//                if (StompCommand.SUBSCRIBE.equals(accessor.getCommand())) {
//                    Principal user = accessor.getUser();
//                    System.out.println("📬 SUBSCRIPTION DETECTED");
//                    System.out.println("   User: " + (user != null ? user.getName() : "null"));
//                    System.out.println("   Destination: " + accessor.getDestination());
//                }
//            }
