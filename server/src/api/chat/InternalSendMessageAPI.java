package api.chat;

import api.API;
import database.Database;
import http.HttpRequest;
import http.HttpSender;
import http.ThreadMessenger;
import models.User;
import utils.JsonUtils;

import java.util.HashMap;
import java.util.Map;

public class InternalSendMessageAPI extends API {
    @Override
    public String getPath() {
        return "/chat/internal/send";
    }

    // Should only be called by other threads
    @Override
    public void handle(HttpRequest request, HttpSender sender, Database database) {
        // Parse request
        Map<String, Object> body = request.body;
        String chatroomId = (String) body.get("id");
        String type = (String) body.get("type");
        String content = (String) body.get("content");
        if (chatroomId == null || type == null || content == null) {
            sender.response(400, "Incorrect request format.");
            return;
        }

        try {
            sender.response(200, JsonUtils.toJson(request.body));
        } catch (Exception e) {
            Map<String, String> output = new HashMap<>();
            output.put("error", e.getMessage());
            sender.response(400, JsonUtils.toJson(output));
        }
    }
}