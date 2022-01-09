package models;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class User {
    public User(String id, String username, String displayName, String password, List<Friend> friends, List<String> chatroomIds) {
        this.id = id;
        this.username = username;
        this.displayName = displayName;
        this.password = password;
        this.friends = friends;
    }

    public User(Map<String, Object> map) {
        this.id = (String) map.get("id");
        this.username =(String) map.get("username");
        this.displayName = (String) map.get("displayName");
        this.password = (String) map.get("password");
        this.chatroomIds = (List<String>) map.get("chatroomIds");
        this.friends = new ArrayList<>();
        List<Map<String, Object>> rawFriends = (List<Map<String, Object>>) map.get("friends");
        for(Map<String, Object> rawFriend : rawFriends) {
            this.friends.add(new Friend(rawFriend));
        }
    }

    public String id;
    public String username;
    public String displayName;
    public String password;
    public List<Friend> friends;
    public List<String> chatroomIds;
}
