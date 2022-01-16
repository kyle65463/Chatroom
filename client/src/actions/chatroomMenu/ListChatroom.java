package actions.chatroomMenu;

import actions.VoidAction;
import http.HttpMessage;
import http.HttpReceiver;
import http.HttpResponse;
import http.HttpSender;
import models.Auth;
import models.Chatroom;
import utils.JsonUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListChatroom extends VoidAction {
    public ListChatroom() {
        this.showIndex = false;
    }

    public ListChatroom(boolean showIndex) {
        this.showIndex = showIndex;
    }

    public void perform(Auth auth, HttpSender sender, HttpReceiver receiver) {
        getChatroomList(auth, sender, receiver);
    }

    public List<Chatroom> getChatroomList(Auth auth, HttpSender sender, HttpReceiver receiver) {
        sender.post("/chatroom/list", JsonUtils.toJson(new HashMap<>()), auth.authToken);

        try {
            HttpMessage message = receiver.readMessage();
            if(message instanceof HttpResponse response) {
                if(response.status == 200) {
                    List<Map<String, Object>> rawChatrooms = (List<Map<String, Object>>) response.body.get("chatrooms");
                    List<Chatroom> chatrooms = rawChatrooms.stream().map(Chatroom::new).toList();
                    System.out.println("Chat rooms:");
                    if(chatrooms.size() == 0) {
                        System.out.println("No chat rooms");
                    }
                    int i = 1;
                    for(Chatroom chatroom : chatrooms) {
                        String prefix = "";
                        if(showIndex) {
                            prefix = "(" + i + ") ";
                        }
                        System.out.println(prefix + chatroom.name + " (" + chatroom .usernames.size() + " users) " + "id=" + chatroom.id);
                        i++;
                    }
                    System.out.println("");
                    return chatrooms;
                }
                else {
                    // Request failed
                    System.out.println(response.body.get("error"));
                }
            }
        }
        catch (Exception ignored) {
        }
        return new ArrayList<>();
    }

    private final boolean showIndex;
}
