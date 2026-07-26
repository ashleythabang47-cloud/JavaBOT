package com.example.chatbot.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatMessage {
    private String id;
    private String sender;
    private String content;
    private MessageType type;
    private LocalDateTime timestamp;

    public enum MessageType {
        USER, BOT, SYSTEM, TYPING
    }
}
