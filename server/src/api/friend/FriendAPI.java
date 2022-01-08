package api.friend;

import api.API;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import database.Database;
import http.HttpRequest;
import http.HttpSender;
import models.User;
import utils.JsonUtils;

import java.util.HashMap;
import java.util.Map;

public class FriendAPI extends API {
    @Override
    public String getPath() {
        return "/friend";
    }

    @Override
    public void handle(HttpRequest request, HttpSender sender, Database database) {
        Map<String, Object> body = request.body;
        String username = (String) body.get("username");
        String password = (String) body.get("password");
        if(username == null || password == null) {
            sender.response(400, "Incorrect request format.");
            return;
        }

        User user;
        try {
            user = database.getUser(username, password);
            String authToken = generateJWTToken(user.id);
            sender.response(200, JsonUtils.toJson(new LoginAPIResponse(authToken, user)));
        }
        catch (Exception e) {
            Map<String, String> output = new HashMap<>();
            output.put("error", e.getMessage());
            sender.response(400, JsonUtils.toJson(output));
        }
    }

    public void addFriend(User user, HttpRequest request, HttpSender sender, Database database) {
        String username = (String) request.body.get("username");

    }

    public void listFriend(User user,HttpRequest request, HttpSender sender, Database database) {

    }



    private String generateJWTToken(String userId) {
        Algorithm algorithm = Algorithm.HMAC256("secret");
        return JWT.create()
                .withClaim("userId", userId)
                .sign(algorithm);
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