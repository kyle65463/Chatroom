package actions.home;

import http.HttpMessage;
import http.HttpReceiver;
import http.HttpResponse;
import http.HttpSender;
import models.Auth;
import models.Friend;
import utils.JsonUtils;
import utils.Scanner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DeleteFriend extends HomeAction {
    public void perform(Auth auth, HttpSender sender, HttpReceiver receiver) {
        Map<String, String> params = new HashMap<>();
        System.out.println("Enter friend's username:");
        String username = Scanner.instance.nextLine();
        while(username.trim().length() == 0) {
            System.out.println("Username is required.");
            username = Scanner.instance.nextLine();
        }
        params.put("username", username.trim());
        sender.post("/friends/delete", JsonUtils.toJson(params), auth.authToken);

        try {
            HttpMessage message = receiver.readMessage();
            if(message instanceof HttpResponse response) {
                if(response.status == 200) {
                    List<Map<String, Object>> rawFriends = (List<Map<String, Object>>) response.body.get("friends");
                    auth.user.friends = rawFriends.stream().map(Friend::new).toList();
                    System.out.println("Deleted successfully");
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
