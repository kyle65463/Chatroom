package api.chat;

import api.API;
import database.Database;
import http.HttpRequest;
import http.HttpSender;
import http.ThreadMessenger;
import models.Chatroom;
import models.User;
import models.chat.ChatHistory;
import models.chat.ChatMessage;
import models.chat.FileMessage;
import models.chat.ImageMessage;
import utils.JsonUtils;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class GetChatHistoriesAPI extends API {
    @Override
    public String getPath() {
        // Receiving message in servers perspective, sending message in client's perspective
        return "/chat/histories";
    }

    @Override
    public void handle(HttpRequest request, HttpSender sender, Database database, ThreadMessenger threadMessenger) {
        // Authenticate token
        User user = authenticate(request, database);
        if(user == null) {
            sender.response(400, request.path,"No authorization.");
            return;
        }

        // Parse request
        Map<String, Object> body = request.body;
        String chatroomId = (String) body.get("id");
        int limit = Integer.parseInt((String) body.get("limit"));
        if(chatroomId == null) {
            sender.response(400, request.path, "Incorrect request format.");
            return;
        }

        try {
            ChatHistory history = database.getChatHistories(chatroomId, limit);
            sender.response(200, request.path, JsonUtils.toJson(history));
        }
        catch (Exception e) {
            Map<String, String> output = new HashMap<>();
            output.put("error", e.getMessage());
            sender.response(400, request.path, JsonUtils.toJson(output));
        }
    }
}