package com.thinhtran.chatapp.config;

import java.util.Collections;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

import com.thinhtran.chatapp.util.SecurityUtil;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class WebSocketAuthInterceptorConfig implements ChannelInterceptor {

    private final SecurityUtil securityUtil;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {

        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        if (StompCommand.CONNECT.equals(accessor.getCommand())) {

            String authHeader = accessor.getFirstNativeHeader("Authorization");

            if (authHeader != null && authHeader.startsWith("Bearer ")) {

                String token = authHeader.substring(7);

                // Extract userId from JWT token directly
                var userId = securityUtil.extractUserIdFromToken(token);

                if (userId.isPresent()) {
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            userId.get().toString(),
                            null,
                            Collections.emptyList());

                    accessor.setUser(authentication);
                    System.out.println("WebSocket user authenticated: " + userId.get());
                } else {
                    System.err.println("Failed to extract userId from WebSocket token");
                }
            } else {
                System.err.println("Missing or invalid Authorization header in WebSocket CONNECT");
            }
        }

        return message;
    }
}
