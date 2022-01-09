package database;

import models.Chatroom;
import models.Friend;
import models.User;

import java.util.List;
import java.util.Map;

public abstract class Database {
    public abstract User createUser(String displayName, String username, String password) throws Exception;
    public abstract User getUser(String username, String password) throws Exception;
    public abstract User getUser(String username) throws Exception;
    public abstract void updateUser(User user, Map<String, Object> update) throws Exception;
    public abstract List<Friend> getFriends(List<String> usernames) throws Exception;

    public abstract Chatroom createChatroom(String chatroomName, String username) throws Exception;
    public abstract void updateChatroom(Chatroom chatroom) throws Exception;
    public abstract Chatroom getChatroom(String id) throws Exception;
}
