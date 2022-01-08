package models;

import utils.JsonUtils;

import java.util.Map;

public class Auth {
    public Auth(User user, String authToken) {
        this.user = user;
        this.authToken = authToken;
    }

    public Auth(Map<String, Object> map) {
        this.authToken = (String) map.get("authToken");
        this.user = new User((Map<String, Object>) map.get("user"));
    }

    public final User user;
    public final String authToken;
}
