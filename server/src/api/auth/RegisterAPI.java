package api.auth;

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

public class RegisterAPI extends API {
    @Override
    public String getPath() {
        return "/register";
    }

    @Override
    public void handle(HttpRequest request, HttpSender sender, Database database) {
        Map<String, Object> body = request.body;
        String username = (String) body.get("username");
        String password = (String) body.get("password");
        String displayName = (String) body.get("password");
        if(username == null || password == null || displayName == null) {
            sender.response(400, "Incorrect request format.");
            return;
        }

        try {
            User user = database.createUser(displayName, username, password);
            String authToken = generateJWTToken(user.id);
            sender.response(200, JsonUtils.toJson(new RegisterAPIResponse(authToken, user)));
        }
        catch (Exception e) {
            Map<String, String> output = new HashMap<>();
            output.put("error", e.getMessage());
            sender.response(400, JsonUtils.toJson(output));
        }
    }

    private String generateJWTToken(String userId) {
        Algorithm algorithm = Algorithm.HMAC256("secret");
        return JWT.create()
                .withClaim("userId", userId)
                .sign(algorithm);
    }
}

class RegisterAPIResponse {
    public RegisterAPIResponse(String authToken, User user) {
        this.authToken = authToken;
        this.user = user;
    }

    public final String authToken;
    public final User user;
}