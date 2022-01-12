package models.chat;

public class ImageMessage extends FileMessage {
    public ImageMessage(String id, byte[] image, String filename, String sender, String chatroomId) {
        super(id, "image", image, filename, sender, chatroomId);
    }

    public static String getType() {
        return "image";
    }
}
