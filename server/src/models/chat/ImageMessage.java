package models.chat;

public class ImageMessage extends ChatMessage {
    public ImageMessage(String id, String content) {
        super(id, "image", content);
    }

    public static String getType() {
        return "image";
    }
}
