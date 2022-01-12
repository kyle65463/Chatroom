package models.chat;

public class ChatMessageFactory {
    public static ChatMessage parse(String chatroomId, String messageId, String sender, String type, String content, String filename) {
        if(type.equals(TextMessage.getType())) {
            return new TextMessage(messageId, content, sender, chatroomId);
        }
        else {
            return new FileMessage(messageId, type, null, filename, sender, chatroomId);
        }
    }
}
