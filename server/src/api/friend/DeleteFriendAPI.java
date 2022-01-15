package api.friend;

import api.API;
import database.Database;
import http.HttpRequest;
import http.HttpSender;
import models.User;
import utils.JsonUtils;

import java.util.*;

public class DeleteFriendAPI extends API {
    @Override
    public String getPath() {
        return "/friend/delete";
    }

    @Override
    public void handle(HttpRequest request, HttpSender sender, Database database) {
        // Authenticate token
        User user = authenticate(request, database);
        if(user == null) {
            sender.response(400, request.path, "No authorization.");
            return;
        }

        // Parse request
        Map<String, Object> body = request.body;
        String friendId = (String) body.get("username");
        if(friendId == null) {
            sender.response(400, request.path, "Incorrect request format.");
            return;
        }

        // Check if not have the friend
        if(!user.friendIds.contains(friendId)) {
            sender.response(400, request.path, "You don't have this friend.");
            return;
        }

        try {
            // Update user
            user.friendIds.removeIf(id -> id.compareTo(friendId) == 0);
            database.updateUser(user);
            sender.response(200, request.path, JsonUtils.toJson(new HashMap<>()));
        }
        catch (Exception e) {
            Map<String, String> output = new HashMap<>();
            output.put("error", e.getMessage());
            sender.response(400, request.path, JsonUtils.toJson(output));
        }
    }
}