package models;

import java.util.List;

public class Chatroom {
    public Chatroom(String id, String name, List<String> usernames) {
        this.id = id;
        this.name = name;
        this.usernames = usernames;
    }

    public final String id;
    public final String name;
    public final List<String> usernames;
}
