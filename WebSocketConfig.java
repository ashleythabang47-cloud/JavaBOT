package com.example.chatbot.service;

import com.example.chatbot.model.ChatMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class ChatbotService {

    @Value("${app.bot-name:JavaBot}")
    private String botName;

    @Value("${app.welcome-message}")
    private String welcomeMessage;

    private final ObjectMapper objectMapper;
    private final Map<String, List<ChatMessage>> sessionHistory = new ConcurrentHashMap<>();

    private final Random random = new Random();

    private static final List<String> JOKES = Arrays.asList(
        "Why do Java developers wear glasses? Because they do not C#.",
        "A SQL query walks into a bar, walks up to two tables and asks: Can I join you?",
        "Why was the function sad? It did not get any calls back.",
        "I told my computer I needed a break. Now it will not stop sending me Kit-Kats.",
        "Why do programmers prefer dark mode? Because light attracts bugs.",
        "How many programmers does it take to change a light bulb? None, that is a hardware problem.",
        "Why did the developer go broke? Because he used up all his cache.",
        "What is a programmer's favorite hangout place? The Foo Bar."
    );

    public List<ChatMessage> getHistory(String sessionId) {
        return sessionHistory.getOrDefault(sessionId, new ArrayList<>());
    }

    public ChatMessage createWelcomeMessage(String sessionId) {
        ChatMessage msg = ChatMessage.builder()
            .id(UUID.randomUUID().toString())
            .sender(botName)
            .content(welcomeMessage)
            .type(ChatMessage.MessageType.SYSTEM)
            .timestamp(LocalDateTime.now())
            .build();
        saveMessage(sessionId, msg);
        return msg;
    }

    @SneakyThrows
    public ChatMessage processUserMessage(String sessionId, String rawPayload) {
        ChatMessage userMsg = objectMapper.readValue(rawPayload, ChatMessage.class);
        userMsg.setId(UUID.randomUUID().toString());
        userMsg.setTimestamp(LocalDateTime.now());
        userMsg.setType(ChatMessage.MessageType.USER);
        saveMessage(sessionId, userMsg);

        String reply = generateReply(userMsg.getContent());

        ChatMessage botMsg = ChatMessage.builder()
            .id(UUID.randomUUID().toString())
            .sender(botName)
            .content(reply)
            .type(ChatMessage.MessageType.BOT)
            .timestamp(LocalDateTime.now())
            .build();
        saveMessage(sessionId, botMsg);

        return botMsg;
    }

    private String generateReply(String input) {
        String lower = input.toLowerCase();

        if (lower.matches(".*\b(hi|hello|hey|greetings|yo)\b.*")) {
            return pickOne(
                "Hey there!",
                "Hello! Ready to chat?",
                "Hi! What is on your mind?"
            );
        }

        if (lower.matches(".*\b(time|clock|hour|what time)\b.*")) {
            return "It is currently " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")) + ".";
        }

        if (lower.matches(".*\b(date|day|today)\b.*")) {
            return "Today is " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("EEEE, MMMM d, yyyy")) + ".";
        }

        if (lower.matches(".*\b(joke|funny|laugh|humor)\b.*")) {
            return JOKES.get(random.nextInt(JOKES.size()));
        }

        if (lower.matches(".*\b(weather|temperature|rain|sunny)\b.*")) {
            return "I cannot check live weather yet, but you can integrate OpenWeatherMap API easily!";
        }

        if (lower.matches(".*\b(name|who are you|what are you)\b.*")) {
            return "I am " + botName + ", your friendly Spring Boot chatbot!";
        }

        if (lower.matches(".*\b(help|commands|what can you do)\b.*")) {
            return "Here is what I can do: say hi, tell time/date, tell jokes, or chat about anything!";
        }

        if (lower.matches(".*\b(bye|goodbye|see you|cya)\b.*")) {
            return "Goodbye! Come back anytime.";
        }

        return pickOne(
            "Interesting! Tell me more.",
            "I am not sure I understand that yet, but I am learning!",
            "Can you rephrase that? Or type help to see what I can do.",
            "Hmm... Try asking me for a joke or the time!"
        );
    }

    private String pickOne(String... options) {
        return options[random.nextInt(options.length)];
    }

    private void saveMessage(String sessionId, ChatMessage msg) {
        sessionHistory.computeIfAbsent(sessionId, k -> new ArrayList<>()).add(msg);
    }
}
