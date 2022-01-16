package api.chat;

import api.API;
import database.Database;
import http.HttpRequest;
import http.HttpSender;
import utils.JsonUtils;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class DownloadMessageAPI extends API {
    @Override
    public String getPath() {
        return "/chat/download";
    }

    // Should only be called by other threads
    @Override
    public void handle(HttpRequest request, HttpSender sender, Database database) {
        System.out.println("download");
        // Parse request
        Map<String, Object> body = request.body;
        String chatroomId = (String) body.get("id");
        String filename = (String) body.get("filename");
        String messageId = (String) body.get("messageId");
        String type = (String) body.get("type");
        if (chatroomId == null || messageId == null || type == null) {
            sender.response(400, request.path,"Incorrect request format.");
            return;
        }


        try {
            System.out.println("abc");
            byte[] file = database.downloadFile(type, messageId);
            String fileStr = Base64.getEncoder().encodeToString(file);
            System.out.println("get file success");
            Map<String, Object> output = new HashMap<>();
            output.put("file", fileStr);
            output.put("filename", filename);
            output.put("type", type);
            output.put("id", messageId);
            System.out.println(fileStr.length());
            sender.response(200, request.path, JsonUtils.toJson(output));
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, String> output = new HashMap<>();
            output.put("error", e.getMessage());
            sender.response(400, request.path, JsonUtils.toJson(output));
        }
    }
}