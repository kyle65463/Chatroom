package actions.chatroom;

import actions.VoidAction;
import http.HttpMessage;
import http.HttpReceiver;
import http.HttpResponse;
import http.HttpSender;
import models.Auth;
import utils.JsonUtils;
import utils.Scanner;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class SendFileMessage extends VoidAction {
    public void perform(Auth auth, HttpSender sender, HttpReceiver receiver) {
        try {
            Map<String, String> params = new HashMap<>();
            System.out.println("Enter file path:");
            String filePathStr = Scanner.getRequiredData("File path");
            Path filePath = Paths.get(filePathStr);

            String filename = filePath.getFileName().toString();
            byte[] file = Files.readAllBytes(filePath);
            String fileStr = new String(file, StandardCharsets.UTF_8);
            params.put("id", "1f6c335");
            params.put("type", "file");
            params.put("filename", filename);
            params.put("content", fileStr);
            sender.post("/chat/send", JsonUtils.toJson(params), auth.authToken);
        }
        catch (Exception e) {
            System.out.println("Read file error.");
        }

        try {
            HttpMessage message = receiver.readMessage();
            if(message instanceof HttpResponse response) {
                if(response.status == 200) {
                    System.out.println("SEND OK");
                }
                else {
                    // Request failed
                    System.out.println(response.body.get("error"));
                }
            }
        }
        catch (Exception ignored) {
        }
    }
}
