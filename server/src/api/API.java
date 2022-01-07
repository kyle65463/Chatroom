package api;

import database.Database;
import http.HttpRequest;
import http.HttpSender;

public abstract class API {
    public final String path = "";

    public abstract void handle(HttpRequest request, HttpSender sender, Database database);
}
