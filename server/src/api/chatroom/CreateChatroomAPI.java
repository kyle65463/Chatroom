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

public class CreateChatroomAPI extends API {
    @Override
    public String getPath() {
        return "/chatroom/create";
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
        String chatroomName = (String) body.get("name");
        if(chatroomName == null) {
            sender.response(400, request.path, "Incorrect request format.");
            return;
        }

        try {
            // Create chatroom
            Chatroom chatroom = database.createChatroom(chatroomName, user.username);
            user.chatroomIds.add(chatroom.id);
            database.updateUser(user);
            sender.response(200, request.path, JsonUtils.toJson(chatroom));
        }
        catch (Exception e) {
            Map<String, String> output = new HashMap<>();
            output.put("error", e.getMessage());
            sender.response(400, request.path, JsonUtils.toJson(output));
        }
    }
}

