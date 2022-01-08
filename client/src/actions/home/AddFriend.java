package actions.home;

import actions.Action;
import http.HttpMessage;
import http.HttpReceiver;
import http.HttpResponse;
import http.HttpSender;
import models.Auth;
import utils.JsonUtils;
import utils.Scanner;

import java.util.HashMap;

import java.util.Map;

public class AddFriend extends HomeAction {
    public void perform(Auth auth, HttpSender sender, HttpReceiver receiver) {
        Map<String, String> params = new HashMap<>();
        System.out.println("Enter friend's username:");
        String username = Scanner.instance.nextLine();
        while(username.trim().length() == 0) {
            System.out.println("Username is required.");
            username = Scanner.instance.nextLine();
        }
        params.put("username", username.trim());
        sender.post("/friends", JsonUtils.toJson(params), auth.authToken);

        try {
            HttpMessage message = receiver.readMessage();
            if(message instanceof HttpResponse response) {
                if(response.status == 200) {
                    System.out.println("Added successfully");
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
