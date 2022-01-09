package models.chat;

public class ChatMessageFactory {
    public static ChatMessage parse(String id, String type, String content) {
        if(type.compareTo(TextMessage.getType()) == 0) {
            return new TextMessage(id, content);
        }
        if(type.compareTo(ImageMessage.getType()) == 0) {
            return new ImageMessage(id, content);
        }
        if(type.compareTo(FileMessage.getType()) == 0) {
            return new FileMessage(id, content);
        }
        return new TextMessage(id, content);
    }
}
