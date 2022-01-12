package models.chat;

public class TextMessage extends ChatMessage {
    public TextMessage(String id, String content, String sender, String chatroomId) {
        super(id, "text", sender, chatroomId);
        this.content = content;
    }

    public final String content;

    public static String getType() {
        return "text";
    }
}
