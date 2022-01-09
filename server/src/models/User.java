package models;

import java.util.List;
import java.util.Map;

public class User {
    public User(String id, String username, String displayName, String password, List<String> friendIds, List<String> chatroomIds) {
        this.id = id;
        this.username = username;
        this.displayName = displayName;
        this.password = password;
        this.friendIds = friendIds;
    }

    public User(Map<String, Object> map) {
        this.id = (String) map.get("id");
        this.username =(String) map.get("username");
        this.displayName = (String) map.get("displayName");
        this.password = (String) map.get("password");
        this.chatroomIds = (List<String>) map.get("chatroomIds");
        this.friendIds = (List<String>) map.get("friendIds");
    }

    public String id;
    public String username;
    public String displayName;
    public String password;
    public List<String> friendIds;
    public List<String> chatroomIds;
}
