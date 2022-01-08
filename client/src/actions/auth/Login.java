package actions.auth;

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

public class Login extends AuthAction {
    public Auth perform(HttpSender sender, HttpReceiver receiver) {
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

        Map<String, String> params = new HashMap<>();
        params.put("username", username.trim());
        params.put("password", password.trim());
        sender.post("/login", JsonUtils.toJson(params));

        try {
            HttpMessage message = receiver.readMessage();
            if(message instanceof HttpResponse response) {
                if(response.status == 200) {
                    return new Auth(response.body);
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
