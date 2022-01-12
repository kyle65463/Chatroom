package database;

import models.Chatroom;
import models.Friend;
import models.User;
import models.chat.ChatHistory;
import models.chat.ChatMessage;

import java.io.IOException;
import java.util.List;

public abstract class Database {
    public abstract User createUser(String displayName, String username, String password) throws Exception;
    public abstract User getUser(String username, String password) throws Exception;
    public abstract User getUser(String username) throws Exception;
    public abstract void updateUser(User user) throws Exception;
    public abstract List<Friend> getFriends(List<String> usernames) throws Exception;

    public abstract Chatroom createChatroom(String chatroomName, String username) throws Exception;
    public abstract void updateChatroom(Chatroom chatroom) throws Exception;
    public abstract Chatroom getChatroom(String id) throws Exception;
    public abstract List<Chatroom> getChatrooms(List<String> ids) throws Exception;

    public abstract ChatHistory getChatHistories(String chatroomId, int limit) throws Exception;
    public abstract ChatMessage addFileMessage(String chatroomId, String sender, String type, byte[] file, String filename) throws Exception;
    public abstract ChatMessage addTextMessage(String chatroomId, String sender, String content) throws Exception;
}
