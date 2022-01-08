package models;

import java.util.Map;

public class User {
    public User(String id, String username, String displayName, String password) {
        this.id = id;
        this.username = username;
        this.displayName = displayName;
        this.password = password;
    }

    public User(Map<String, Object> map) {
        this.id = (String) map.get("id");
        this.username =(String) map.get("username");
        this.displayName = (String) map.get("displayName");
        this.password = (String) map.get("password");
    }

    public String id;
    public String username;
    public String displayName;
    public String password;

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", displayName='" + displayName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
