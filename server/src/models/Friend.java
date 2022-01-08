package models;

import java.util.Map;

public class Friend {
    public Friend(String username, String displayName) {
        this.username = username;
        this.displayName = displayName;
    }

    public Friend(Map<String, Object> map) {
        this.username =(String) map.get("username");
        this.displayName = (String) map.get("displayName");
    }

    public String username;
    public String displayName;
}
