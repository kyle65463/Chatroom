package api.friends;

import api.API;
import database.Database;
import http.HttpRequest;
import http.HttpSender;
import models.Friend;
import models.User;
import utils.JsonUtils;

import java.util.*;

public class AddFriendAPI extends API {
    @Override
    public String getPath() {
        return "/friends/add";
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

        // Check if already have the same friend
        List<String> friendUsernames = new ArrayList<>();
        for(Friend friend : user.friends) {
            if(friend.username.compareTo(username) == 0) {
                sender.response(400, "You already have this friend.");
                return;
            }
            friendUsernames.add(friend.username);
        }

        try {
            List<Friend> newFriends = database.getFriends(Collections.singletonList(username));
            if(newFriends.size() == 0) {
                sender.response(400, "User not found.");
                return;
            }
            Friend newFriend = newFriends.get(0);
            friendUsernames.add(newFriend.username);
            Map<String, Object> update = new HashMap<>();
            update.put("friends", friendUsernames);
            database.updateUser(user, update);
            sender.response(200, JsonUtils.toJson(new AddFriendAPIResponse(newFriend)));
        }
        catch (Exception e) {
            Map<String, String> output = new HashMap<>();
            output.put("error", e.getMessage());
            sender.response(400, JsonUtils.toJson(output));
        }
    }
}

class AddFriendAPIResponse {
    public AddFriendAPIResponse(Friend friend) {
        this.friend = friend;
    }

    public final Friend friend;
}