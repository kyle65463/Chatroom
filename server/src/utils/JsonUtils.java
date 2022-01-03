package utils;

import java.util.*;

import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JsonUtils {
    public static JSONObject parseJSON(String str) throws ParseException {
        JSONParser parser = new JSONParser();
        return (JSONObject) parser.parse(str);
    }

    public static Map<String, Object> toMap(JSONObject object) {
        Map<String, Object> map = new HashMap<>();
        for (String key : (Iterable<String>) object.keySet()) {
            Object value = object.get(key);
            if (value instanceof JSONArray) {
                value = toList((JSONArray) value);
            } else if (value instanceof JSONObject) {
                value = toMap((JSONObject) value);
            }
            map.put(key, value);
        }
        return map;
    }

    public static List<Object> toList(JSONArray array) {
        List<Object> list = new ArrayList<Object>();
        for (Object value : array) {
            if (value instanceof JSONArray) {
                value = toList((JSONArray) value);
            } else if (value instanceof JSONObject) {
                value = toMap((JSONObject) value);
            }
            list.add(value);
        }
        return list;
    }
}