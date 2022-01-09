package models.chat;

import java.util.List;

public class ChatHistory {
    public ChatHistory(List<ChatMessage> messages, boolean isLast) {
        this.messages = messages;
        this.isLast = isLast;
    }

    public final List<ChatMessage> messages;
    public final boolean isLast;
}
