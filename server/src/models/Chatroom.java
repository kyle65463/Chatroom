package models;

import java.util.List;
import java.util.Map;

public class Chatroom {
    public Chatroom(String id, String name, List<String> usernames) {
        this.id = id;
        this.name = name;
        this.usernames = usernames;
    }

    public  Chatroom(Map<String, Object> map) {
        this.id =(String) map.get("id");
        this.name= (String) map.get("name");
        this.usernames = (List<String>) map.get("usernames");
    }

    public final String id;
    public final String name;
    public final List<String> usernames;
}
