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

public class Login extends Action {
    public String perform(HttpSender sender, HttpReceiver receiver) {
        System.out.println("Enter username:");
        String username = Scanner.instance.nextLine();

        System.out.println("Enter password:");
        String password = Scanner.instance.nextLine();

        Map<String, String> params = new HashMap<>();
        params.put("username", username);
        params.put("password", password);
        sender.post("/login", JsonUtils.toJson(params));

        try {
            HttpMessage message = receiver.readMessage();
            if(message instanceof HttpResponse response) {
                if(response.status == 200) {
                    return (String) response.body.get("authToken");
                }
            }
        }
        catch (Exception ignored) {
        }
        return null;
    }
}
