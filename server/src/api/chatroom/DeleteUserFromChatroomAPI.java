package api.chatroom;

import api.API;
import database.Database;
import http.HttpRequest;
import http.HttpSender;
import models.Chatroom;
import models.User;
import utils.JsonUtils;

import java.util.HashMap;
import java.util.Map;

public class DeleteUserFromChatroomAPI extends API {
    @Override
    public String getPath() {
        return "/chatroom/user/delete";
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
        String chatroomId = (String) body.get("id");
        if(chatroomId == null) {
            sender.response(400, "Incorrect request format.");
            return;
        }

        // Check if not have the chatroom
        if(!user.chatroomIds.contains(chatroomId)) {
            sender.response(400, "You don't have this friend.");
            return;
        }

        try {
            // Update chatroom
            Chatroom chatroom = database.getChatroom(chatroomId);
            chatroom.usernames.removeIf(username -> username.compareTo(user.username) == 0);
            database.updateChatroom(chatroom);

            // Update user
            user.chatroomIds.removeIf(id -> id.compareTo(chatroomId) == 0);
            database.updateUser(user);
            sender.response(200, JsonUtils.toJson(new HashMap<>()));
        }
        catch (Exception e) {
            Map<String, String> output = new HashMap<>();
            output.put("error", e.getMessage());
            sender.response(400, JsonUtils.toJson(output));
        }
    }
}

