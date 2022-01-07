package api.auth;

import api.API;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import database.Database;
import http.HttpRequest;
import http.HttpSender;
import models.User;
import utils.JsonUtils;

import java.util.Map;

public class LoginAPI extends API {
    public final String path = "/login";

    @Override
    public void handle(HttpRequest request, HttpSender sender, Database database) {
        Map<String, Object> body = request.body;
        String username = (String) body.get("username");
        String password = (String) body.get("password");
        if(username == null || password == null) {
            sender.response(400, "Incorrect format");
            return;
        }

        User user = database.getUser(username, password);
        if(user == null) {
            sender.response(500, "Get user error");
            return;
        }

        String token = generateJWTToken(user.id);
        sender.response(200, JsonUtils.toJson(new Response(token, user)));
    }

    private String generateJWTToken(String userId) {
        Algorithm algorithm = Algorithm.HMAC256("secret");
        return JWT.create()
                .withClaim("userId", userId)
                .sign(algorithm);
    }
}

class Response {
    public Response(String token, User user) {
        this.token = token;
        this.user = user;
    }

    public final String token;
    public final User user;
}