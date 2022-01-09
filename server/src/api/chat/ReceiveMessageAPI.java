package api.chat;

import api.ChatMessageAPI;
import database.Database;
import http.HttpRequest;
import http.HttpSender;
import models.User;
import utils.JsonUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

public class ReceiveMessageAPI extends ChatMessageAPI {
    @Override
    public String getPath() {
        // Receiving message in servers perspective, sending message in client's perspective
        return "/chat/send";
    }

    @Override
    public void handle(HttpRequest request, HttpSender sender, Database database, BlockingQueue<String> messageQueue) {
        // Authenticate token
        User user = authenticate(request, database);
        if(user == null) {
            sender.response(400, "No authorization.");
            return;
        }

        // Parse request
        Map<String, Object> body = request.body;
        String chatroomId = (String) body.get("id");
        String type = (String) body.get("type");
        String content = (String) body.get("content");
        if(chatroomId == null || type == null ||  content == null) {
            sender.response(400, "Incorrect request format.");
            return;
        }

        try {
            System.out.println("CONTENT::");
            System.out.println(content);
            messageQueue.put("Thread-2");
            sender.response(200, JsonUtils.toJson(new HashMap<>()));
        }
        catch (Exception e) {
            Map<String, String> output = new HashMap<>();
            output.put("error", e.getMessage());
            sender.response(400, JsonUtils.toJson(output));
        }
    }
}