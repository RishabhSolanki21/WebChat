package com.example.WebChat.Configurations;
import com.example.WebChat.Security.MyChannel_Interceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;
import org.springframework.web.socket.server.HandshakeInterceptor;

@Configuration
@EnableWebSocketMessageBroker
public class ChatConfig implements WebSocketMessageBrokerConfigurer {

    @Autowired
    private MyChannel_Interceptor MyChannel_Interceptor;

    @Autowired
    private HandshakeInterceptor handshakeInterceptor;

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")
                .setAllowedOrigins("http://localhost:5173")
//                .addInterceptors(handshakeInterceptor)
                .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes("/chat");
        registry.enableSimpleBroker("/topic","/user");
        registry.setUserDestinationPrefix("/user");
    }


    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
       registration.interceptors(MyChannel_Interceptor);
        registration.taskExecutor().corePoolSize(4).maxPoolSize(8);
    }
//    @Override
//    public void configureWebSocketTransport(WebSocketTransportRegistration registration) {
//        registration.setMessageSizeLimit(64 * 1024); // 64KB
//        registration.setSendBufferSizeLimit(512 * 1024); // 512KB
//        registration.setSendTimeLimit(20000); // 20s
//    }
//
//    @Override
//    public void configureClientOutboundChannel(ChannelRegistration registration) {
//        registration.taskExecutor().corePoolSize(4).maxPoolSize(8);
//    }
}
