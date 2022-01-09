package models.chat;

public abstract class ChatMessage {
    protected ChatMessage(String id, String type, String content) {
        this.id = id;
        this.type = type;
        this.content = content;
    }

    public final String id;
    public final String type;
    public final String content;
}
