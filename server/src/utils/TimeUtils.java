package utils;

import java.sql.Timestamp;

public class TimeUtils {
    public static String getCurrentTimeString() {
        return new Timestamp(System.currentTimeMillis()).toString();
    }
}
