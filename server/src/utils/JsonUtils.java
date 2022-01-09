package utils;

import com.google.gson.Gson;

import java.util.Map;

public class JsonUtils {
   public static Map<String, Object> jsonToMap(String json) {
       Gson gson = new Gson();
       return (Map<String, Object>) gson.fromJson(json, Map.class);
   }

    public static String toJson(Object obj) {
        Gson gson = new Gson();
        return gson.toJson(obj);
    }

    public static Map<String, Object> objToMap(Object obj) {
        return jsonToMap(toJson(obj));
    }
}
