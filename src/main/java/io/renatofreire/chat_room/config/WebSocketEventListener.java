package io.renatofreire.chat_room.config;

import io.renatofreire.chat_room.enums.MessageType;
import io.renatofreire.chat_room.records.ChatMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
public class WebSocketEventListener {

    private final SimpMessageSendingOperations messageTemplate;

    private static final Logger logger = LoggerFactory.getLogger(WebSocketEventListener.class);

    public WebSocketEventListener(SimpMessageSendingOperations messageTemplate) {
        this.messageTemplate = messageTemplate;
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event){
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String username = (String) headerAccessor.getSessionAttributes().get("username");
        if(username != null){
            logger.info("User disconnected");
            ChatMessage userLeftTheChat = new ChatMessage("User left the chat", username, MessageType.DISCONNECT);
            messageTemplate.convertAndSend("/topic/public", userLeftTheChat);
        }

    }

}
