package api.auth;

import api.API;
import database.Database;
import http.HttpRequest;
import http.HttpSender;

public class NotFound extends API {
    @Override
    public String getPath() {
        return "";
    }

    @Override
    public void handle(HttpRequest request, HttpSender sender, Database database) {
        sender.response(404, "Not found");
    }
}
