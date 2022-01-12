package models.chat;

public abstract class ChatMessage {
    protected ChatMessage(String id, String type, String sender, String chatroomId) {
        this.id = id;
        this.type = type;
        this.sender = sender;
        this.chatroomId = chatroomId;
    }

    public final String chatroomId;
    public final String type;
    public final String sender;
    public String id;
}
