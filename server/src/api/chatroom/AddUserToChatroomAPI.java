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

public class AddUserToChatroomAPI extends API {
    @Override
    public String getPath() {
        return "/chatroom/user/add";
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
        String chatroomId = (String) body.get("id");
        String username = (String) body.get("username");
        if(chatroomId == null || username == null) {
            sender.response(400, request.path, "Incorrect request format.");
            return;
        }

        try {
            // Update chatroom
            Chatroom chatroom = database.getChatroom(chatroomId);
            chatroom.usernames.add(username);
            database.updateChatroom(chatroom);

            // Update user
            User newUser = database.getUser(username);
            newUser.chatroomIds.add(chatroomId);
            database.updateUser(newUser);
            sender.response(200, request.path, JsonUtils.toJson(chatroom));
        }
        catch (Exception e) {
            Map<String, String> output = new HashMap<>();
            output.put("error", e.getMessage());
            sender.response(400, request.path, JsonUtils.toJson(output));
        }
    }
}

