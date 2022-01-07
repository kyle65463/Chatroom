package models;

public class User {
    public User(String id, String username, String displayName, String password) {
        this.id = id;
        this.username = username;
        this.displayName = displayName;
        this.password = password;
    }

    public String id;
    public String username;
    public String displayName;
    public String password;
}
