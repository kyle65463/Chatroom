package models.chat;

public class TextMessage extends ChatMessage {
    public TextMessage(String id, String content) {
        super(id, "text", content);
    }

    public static String getType() {
        return "text";
    }
}
