package api;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import database.Database;
import http.HttpRequest;
import http.HttpSender;
import models.User;

public abstract class HttpAPI extends API {
    public abstract void handle(HttpRequest request, HttpSender sender, Database database);
}
