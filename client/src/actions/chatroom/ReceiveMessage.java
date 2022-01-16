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

public class ReceiveMessage extends StateAction {
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
                        System.out.printf("%s");
                        if(chatMessage.get("type").equals("text")) {
                            System.out.println();
                        }
                    }
                    System.out.println(response.body);
                    System.out.println("RECEIVE OK");
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
