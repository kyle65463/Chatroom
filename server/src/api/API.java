package api;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import database.Database;
import http.HttpRequest;
import http.HttpSender;
import models.User;

public abstract class API {
    public abstract String getPath();
    public abstract void handle(HttpRequest request, HttpSender sender, Database database);

    protected User authenticate(HttpRequest request, Database database) {
        String authToken = request.header.get("Authorization");
        try {
            DecodedJWT jwt = JWT.decode(authToken);
            String username = jwt.getClaim("username").asString();
            return database.getUser(username);

        } catch (Exception ignored){
        }
        return null;
    }

    protected String generateJWTToken(String username) {
        Algorithm algorithm = Algorithm.HMAC256("secret");
        return JWT.create()
                .withClaim("username", username)
                .sign(algorithm);
    }
}
