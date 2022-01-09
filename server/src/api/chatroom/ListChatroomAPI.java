package api.chatroom;

import api.HttpAPI;
import database.Database;
import http.HttpRequest;
import http.HttpSender;
import models.Chatroom;
import models.User;
import utils.JsonUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListChatroomAPI extends HttpAPI {
    @Override
    public String getPath() {
        return "/chatroom/list";
    }

    @Override
    public void handle(HttpRequest request, HttpSender sender, Database database) {
        // Authenticate token
        User user = authenticate(request, database);
        if(user == null) {
            sender.response(400, "No authorization.");
            return;
        }

        try {
            // Get chatrooms
            List<Chatroom> chatrooms = database.getChatrooms(user.chatroomIds);
            sender.response(200, JsonUtils.toJson(Collections.singletonMap("chatrooms", chatrooms)));
        }
        catch (Exception e) {
            Map<String, String> output = new HashMap<>();
            output.put("error", e.getMessage());
            sender.response(400, JsonUtils.toJson(output));
        }
    }
}

