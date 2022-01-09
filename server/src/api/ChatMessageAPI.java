package api;

import database.Database;
import http.HttpRequest;
import http.HttpSender;

import java.util.concurrent.BlockingQueue;

public abstract class ChatMessageAPI extends API {
    public abstract void handle(HttpRequest request, HttpSender sender, Database database, BlockingQueue<String> messageQueue);
}
