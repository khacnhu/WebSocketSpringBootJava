package com.example.SpringWebSocket.controller;


import com.example.SpringWebSocket.models.ChatMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessage chatMessage(@Payload ChatMessage chatMessage){
        return chatMessage;

    }

    @MessageMapping("/chat.add")
    @SendTo("/topic/public")
    public ChatMessage addUser(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor){
        // add user in web socket session
        headerAccessor.getSessionAttributes().put("username", chatMessage);
        return chatMessage;
    }


}
