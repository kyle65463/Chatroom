package models.chat;

public class FileMessage extends ChatMessage{
    public FileMessage(String id, String type, byte[] file, String filename, String sender, String chatroomId) {
        super(id, type, sender, chatroomId);
        this.file = file;
        this.filename = filename;
    }

    public byte[] file;
    public String filename;

    public static String getType() {
        return "file";
    }
}
