package models.chat;

public class FileMessage extends ChatMessage{
    public FileMessage(String id, String content) {
        super(id, "file", content);
    }

    public static String getType() {
        return "file";
    }
}
