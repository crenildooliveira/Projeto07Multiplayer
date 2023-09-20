package com.smb.projeto07multiplayer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry config) {
        config.addHandler(gameWebSocketHandler(), "/game").setAllowedOrigins("*");
    }

    @Override
    @Bean
    public WebSocketHandler gameWebSocketHandler() {
        return new GameHandler();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic"); // Prefixo para tópicos de mensagens
        config.setApplicationDestinationPrefixes("/app"); // Prefixo para mensagens enviadas pelo cliente para o servidor
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry config) {
        config.addEndpoint("/game");
        config.addEndpoint("/game").withSockJS(); // Endpoint WebSocket para clientes
    }
}
