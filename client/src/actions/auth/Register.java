package actions.auth;

import actions.Action;
import http.HttpMessage;
import http.HttpReceiver;
import http.HttpResponse;
import http.HttpSender;
import utils.JsonUtils;
import utils.Scanner;

import java.util.HashMap;
import java.util.Map;

public class Register extends Action {
    public String perform(HttpSender sender, HttpReceiver receiver) {
        System.out.println("Enter username:");
        String username = Scanner.instance.nextLine();
        while(username.trim().length() == 0) {
            System.out.println("Username is required.");
            username = Scanner.instance.nextLine();
        }

        System.out.println("Enter password:");
        String password = Scanner.instance.nextLine();
        while(password.trim().length() == 0) {
            System.out.println("Password is required.");
            password = Scanner.instance.nextLine();
        }

        System.out.println("Enter displayName (optional): ");
        String displayName = Scanner.instance.nextLine();
        if(displayName.trim().length() == 0) {
            displayName = username;
        }

        Map<String, String> params = new HashMap<>();
        params.put("username", username.trim());
        params.put("password", password.trim());
        params.put("displayName", displayName.trim());
        sender.post("/register", JsonUtils.toJson(params));

        try {
            HttpMessage message = receiver.readMessage();
            if(message instanceof HttpResponse response) {
                if(response.status == 200) {
                    return (String) response.body.get("authToken");
                }
                else {
                    // Request failed
                    System.out.println(response.body.get("error"));
                }
            }
        }
        catch (Exception ignored) {
        }
        return null;
    }
}
