package actions.home;

import http.HttpMessage;
import http.HttpReceiver;
import http.HttpResponse;
import http.HttpSender;
import models.Auth;
import models.Friend;
import utils.JsonUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListFriend extends HomeAction {
    public void perform(Auth auth, HttpSender sender, HttpReceiver receiver) {
        sender.post("/friend/list", JsonUtils.toJson(new HashMap<>()), auth.authToken);

        try {
            HttpMessage message = receiver.readMessage();
            if(message instanceof HttpResponse response) {
                if(response.status == 200) {
                    List<Map<String, Object>> rawFriends = (List<Map<String, Object>>) response.body.get("friends");
                    List<Friend> friends = rawFriends.stream().map(Friend::new).toList();
                    System.out.println("Friends:");
                    if(friends.size() == 0) {
                        System.out.println("No friends");
                    }
                    for(Friend friend : friends) {
                        System.out.println(friend.displayName + " (" + friend.username + ")");
                    }
                    System.out.println("");
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
