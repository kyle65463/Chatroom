package api.friends;

import api.API;
import database.Database;
import http.HttpRequest;
import http.HttpSender;
import models.Friend;
import models.User;
import utils.JsonUtils;

import java.util.*;

public class DeleteFriendAPI extends API {
    @Override
    public String getPath() {
        return "/friends/delete";
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
        String username = (String) body.get("username");
        if(username == null) {
            sender.response(400, "Incorrect request format.");
            return;
        }

        // Remove friend and check if not have the friend
        int originalNumFriends = user.friends.size();
        user.friends.removeIf(friend -> friend.username.compareTo(username) == 0);
        if(originalNumFriends == user.friends.size()) {
            sender.response(400, "You don't have this friend.");
            return;
        }

        try {
            // Update user
            List<String> friendUsernames = user.friends.stream().map(friend -> friend.username).toList();
            Map<String, Object> update = new HashMap<>();
            update.put("friends", friendUsernames);
            database.updateUser(user, update);
            sender.response(200, JsonUtils.toJson(new FriendsAPIResponse(user.friends)));
        }
        catch (Exception e) {
            Map<String, String> output = new HashMap<>();
            output.put("error", e.getMessage());
            sender.response(400, JsonUtils.toJson(output));
        }
    }
}