package actions.chatroom;

import actions.StateAction;
import http.HttpMessage;
import http.HttpReceiver;
import http.HttpResponse;
import http.HttpSender;
import models.Auth;
import models.chat.TextMessage;
import state.ClientState;
import utils.JsonUtils;
import utils.Scanner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetChatHistories extends StateAction {
    public void perform(ClientState state, Auth auth, HttpSender sender, HttpReceiver receiver) {
        Map<String, String> params = new HashMap<>();
        params.put("id", state.chatroomId);
        params.put("limit", "1");
        sender.post("/chat/histories", JsonUtils.toJson(params), auth.authToken);

        try {
            HttpMessage message = receiver.readMessage();
            if(message instanceof HttpResponse response) {
                if(response.status == 200) {
                    List<Map<Object, String>> messages = (List<Map<Object, String>>) response.body.get("messages");
                    for(Map<Object, String> chatMessage : messages) {
                        String type = chatMessage.get("type");
                        System.out.printf(chatMessage.get("sender") + ": ");
                        if(type.equals("text")) {
                            System.out.println(chatMessage.get("content"));
                        }
                        else {
                            String id = chatMessage.get("id");
                            String filename = chatMessage.get("filename");
                            System.out.println("<" + type + ": "  + filename + ">" + "(id=" + id + ")");

                        }
                    }
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
