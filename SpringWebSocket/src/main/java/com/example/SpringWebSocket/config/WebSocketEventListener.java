package com.example.SpringWebSocket.config;


import com.example.SpringWebSocket.models.ChatMessage;
import com.example.SpringWebSocket.models.MessageType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.SessionListener;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
@RequiredArgsConstructor
@Slf4j
public class WebSocketEventListener {

    private final SimpMessageSendingOperations messageTemplate;

    @EventListener
    public void handleWebSocketDisconnectListener (SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        String username = (String) headerAccessor.getSessionAttributes().get("username");

        if (username != null){
            log.info("User disconnect {} ", username);
            var chatMessage = ChatMessage.builder()
                    .type(MessageType.LEAVER)
                    .sender(username)
                    .build();

            messageTemplate.convertAndSend("/topic/public", chatMessage);

        }
    }


}


