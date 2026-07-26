#  JavaBot — Real-Time WebSocket Chatbot

A real-time chatbot built with **Spring Boot 3**, **WebSocket**, and a built-in **HTML/CSS/JS frontend**. Open your browser, type a message, and get instant replies — no external APIs required.

[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3.0-brightgreen)](https://spring.io/projects/spring-boot)
[![Java](https://img.shields.io/badge/Java-21-orange)](https://openjdk.org/projects/jdk/21/)
[![WebSocket](https://img.shields.io/badge/WebSocket-Real--time-blue)](https://developer.mozilla.org/en-US/docs/Web/API/WebSockets_API)

---

##  Features

| Feature | Description |
|---------|-------------|
|  **Real-time Chat** | WebSocket-powered bidirectional messaging |
|  **Rule-based Bot** | Responds to greetings, time, date, jokes, and more |
|  **Typing Indicator** | Animated dots while the bot "thinks" |
|  **Chat History** | Per-session message storage via REST API |
|  **Dark UI** | Modern, responsive chat interface |
|  **Docker Ready** | Dockerfile included |
|  **Extensible** | Easy to swap in OpenAI/Claude API |

---

##  Quick Start

### Prerequisites
- Java 21+
- Maven 3.9+

### 1. Run

```bash
git clone https://github.com/yourusername/java-chatbot.git
cd java-chatbot
mvn spring-boot:run
```

### 2. Open in Browser

```
http://localhost:8080
```

That is it. The chat UI loads instantly. Start typing!

---

##  Docker

```bash
docker build -t java-chatbot .
docker run -p 8080:8080 java-chatbot
```

---

##  API

### WebSocket Endpoint
```
ws://localhost:8080/ws/chat
```

### Chat History (REST)
```http
GET /api/chat/history/{sessionId}
```

---

##  Bot Commands

| Input | Response |
|-------|----------|
| `hi`, `hello` | Friendly greeting |
| `time` | Current time |
| `date` / `today` | Today's date |
| `joke` | Random programming joke |
| `weather` | Placeholder (ready for API integration) |
| `help` | List of capabilities |
| `bye` | Farewell |

---

##  Project Structure

```
java-chatbot/
├── src/main/java/com/example/chatbot/
│   ├── config/          # WebSocket configuration
│   ├── controller/      # REST endpoints
│   ├── handler/         # WebSocket message handler
│   ├── model/           # ChatMessage entity
│   ├── service/         # Bot brain & logic
│   └── ChatbotApplication.java
├── src/main/resources/
│   ├── static/          # HTML, CSS, JS frontend
│   └── application.yml
├── pom.xml
└── Dockerfile
```

---

##  Integrate OpenAI / Claude

Replace `generateReply()` in `ChatbotService.java` with an API call:

```java
private String generateReply(String input) {
    // Call OpenAI API here
    return openAiClient.chat(input);
}
```

---

##  License

BUILT FOR EDUCATIONAL PURPOSE

---

<p align="center">Built with Spring Boot & WebSocket</p>
