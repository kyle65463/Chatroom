package models;

import java.util.List;
import java.util.Map;

public class User {
    public User(String username, String displayName, String password, List<String> friendIds, List<String> chatroomIds) {
        this.username = username;
        this.displayName = displayName;
        this.password = password;
        this.friendIds = friendIds;
        this.chatroomIds = chatroomIds;
    }

    public User(Map<String, Object> map) {
        this.username =(String) map.get("username");
        this.displayName = (String) map.get("displayName");
        this.password = (String) map.get("password");
        this.friendIds = (List<String>) map.get("friendIds");
        this.chatroomIds = (List<String>) map.get("chatroomIds");
    }

    public String username;
    public String displayName;
    public String password;
    public List<String> friendIds;
    public List<String> chatroomIds;
}
