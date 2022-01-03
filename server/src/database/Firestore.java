package database;

import utils.JsonUtils;

import java.net.*;
import java.net.http.*;
import java.util.HashMap;
import java.util.Map;


public class Firestore extends Database {
    private final String baseUrl = "https://firestore.googleapis.com/v1beta1/projects/chatroom-ece98/databases/(default)/documents/";


    public Map<String, Object> getDoc() {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(baseUrl + "test/rx5xSB0MiNkDMuCAZTJC"))
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String body = response.body();
            return JsonUtils.toMap(JsonUtils.parseJSON(body));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new HashMap<>();
    }
}
