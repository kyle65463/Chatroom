package api.chat;

import api.API;
import database.Database;
import http.HttpRequest;
import http.HttpSender;
import http.ThreadMessenger;
import models.Chatroom;
import models.User;
import models.chat.*;
import utils.JsonUtils;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

public class SendMessageAPI extends API {
    @Override
    public String getPath() {
        // Receiving message in servers perspective, sending message in client's perspective
        return "/chat/send";
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
        String type = (String) body.get("type");
        String content = (String) body.get("content");
        if(chatroomId == null || type == null) {
            sender.response(400, request.path, "Incorrect request format.");
            return;
        }

        // Parse file messages
        String filename = "";
        boolean isFileMessage = false;
        if(type.equals(FileMessage.getType()) || type.equals((ImageMessage.getType()))) {
            filename = (String) body.get("filename");
            if(filename.isEmpty()) {
                sender.response(400, request.path, "Incorrect request format.");
                return;
            }
            isFileMessage = true;
        }

        try {
            ChatMessage message = null;
            if(isFileMessage) {
                message = database.addFileMessage(chatroomId, user.username, type, Base64.getDecoder().decode(content), filename);
            }
            else {
                // Text messages
                message = database.addTextMessage(chatroomId, user.username, content);
            }

            Chatroom chatroom = database.getChatroom(chatroomId);
            for(String username : chatroom.usernames) {
                // Send the message to every user in the chat room, including the sender
                HttpRequest internalRequest = HttpSender.makeRequest("/chat/internal/send", JsonUtils.toJson(message),"post");
                threadMessenger.putMessage(username, internalRequest);
            }
            sender.response(200, request.path, JsonUtils.toJson(new HashMap<>()));
        }
        catch (Exception e) {
            Map<String, String> output = new HashMap<>();
            output.put("error", e.getMessage());
            sender.response(400, request.path, JsonUtils.toJson(output));
        }
    }
}