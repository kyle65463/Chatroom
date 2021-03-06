package api.auth;

import api.API;
import database.Database;
import http.HttpRequest;
import http.HttpSender;
import http.ThreadMessenger;
import models.User;
import utils.JsonUtils;

import java.util.HashMap;
import java.util.Map;

public class LoginAPI extends API {
    @Override
    public String getPath() {
        return "/login";
    }

    @Override
    public void handle(HttpRequest request, HttpSender sender, Database database, ThreadMessenger threadMessenger) {
        Map<String, Object> body = request.body;
        String username = (String) body.get("username");
        String password = (String) body.get("password");
        String realtime = (String) body.get("realtime");
        if(username == null || password == null) {
            sender.response(400, request.path,"Incorrect request format.");
            return;
        }

        User user;
        try {
            user = database.getUser(username, password);
            String authToken = generateJWTToken(user.username);
            System.out.println("Logged in: " + user.username);
            if(realtime != null &&  realtime.equals("true")){
                System.out.println("realtime mode");
                threadMessenger.setUsername(user.username);
            }
            sender.response(200, request.path,JsonUtils.toJson(new LoginAPIResponse(authToken, user)));
        }
        catch (Exception e) {
            Map<String, String> output = new HashMap<>();
            output.put("error", e.getMessage());
            sender.response(400, request.path,JsonUtils.toJson(output));
        }
    }
}

class LoginAPIResponse {
    public LoginAPIResponse(String authToken, User user) {
        this.authToken = authToken;
        this.user = user;
    }

    public final String authToken;
    public final User user;
}