package api.friend;

import api.API;
import database.Database;
import http.HttpRequest;
import http.HttpSender;
import models.User;
import utils.JsonUtils;

import java.util.*;

public class AddFriendAPI extends API {
    @Override
    public String getPath() {
        return "/friend/add";
    }

    @Override
    public void handle(HttpRequest request, HttpSender sender, Database database) {
        // Authenticate token
        User user = authenticate(request, database);
        if(user == null) {
            sender.response(400, "No authorization.");
            return;
        }

        // Parse request
        Map<String, Object> body = request.body;
        String friendId = (String) body.get("id");
        if(friendId == null) {
            sender.response(400, "Incorrect request format.");
            return;
        }

        // Check if already have the same friend
        for(String id : user.friendIds) {
            if(id.compareTo(friendId) == 0) {
                sender.response(400, "You already have this friend.");
                return;
            }
        }

        try {
            // Add friend to user
            user.friendIds.add(friendId);
            database.updateUser(user);
            sender.response(200, JsonUtils.toJson(new FriendsAPIResponse(user.friendIds)));
        }
        catch (Exception e) {
            Map<String, String> output = new HashMap<>();
            output.put("error", e.getMessage());
            sender.response(400, JsonUtils.toJson(output));
        }
    }
}

