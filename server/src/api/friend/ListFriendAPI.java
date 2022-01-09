package api.friend;

import api.HttpAPI;
import database.Database;
import http.HttpRequest;
import http.HttpSender;
import models.Friend;
import models.User;
import utils.JsonUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListFriendAPI extends HttpAPI {
    @Override
    public String getPath() {
        return "/friend/list";
    }

    @Override
    public void handle(HttpRequest request, HttpSender sender, Database database) {
        // Authenticate token
        User user = authenticate(request, database);
        if(user == null) {
            sender.response(400, "No authorization.");
            return;
        }
        System.out.println("LIST");
        System.out.println(user.friendIds);

        try {
            List<Friend> friends = database.getFriends(user.friendIds);
            System.out.println(friends);
            sender.response(200, JsonUtils.toJson(Collections.singletonMap("friends", friends)));
        }
        catch (Exception e) {
            Map<String, String> output = new HashMap<>();
            output.put("error", e.getMessage());
            sender.response(400, JsonUtils.toJson(output));
        }
    }
}