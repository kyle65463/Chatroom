package actions.home;

import http.HttpMessage;
import http.HttpReceiver;
import http.HttpResponse;
import http.HttpSender;
import models.Auth;

import java.util.List;
import java.util.Map;

public class ListFriend {
    public void perform(Auth auth, HttpSender sender, HttpReceiver receiver) {
        sender.get("/friends", "", auth.authToken);
        try {
            HttpMessage message = receiver.readMessage();
            if(message instanceof HttpResponse response) {
                if(response.status == 200) {
                     List<Map<String, String>> friends = (List<Map<String, String>>) response.body.get("friends");
                     int i = 1;
                     for(Map<String, String> friend : friends) {
                         String username = friend.get("username");
                         String displayName = friend.get("displayName");
                         System.out.printf("(%d) %s (%s)%n", i, displayName, username);
                         i++;
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
