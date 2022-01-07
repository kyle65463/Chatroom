package http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpReceiver {
    public HttpReceiver(Socket socket) throws IOException {
        is = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    private final BufferedReader is ;

    public HttpMessage readMessage() throws Exception {
        String startLine = is.readLine();
        Map<String, String> header = new HashMap<>();
        String line = is.readLine();
        while(line.length() != 0) {
            List<String> tokens = Arrays.asList(line.split(":", 2));
            if (tokens.size() == 2) {
                String key = tokens.get(0).trim();
                String value = tokens.get(1).trim();
                header.put(key, value);
            } else {
                return null;
            }
            line = is.readLine();
        }
        int contentLength = Integer.parseInt(header.get("Content-Length"));
        if(contentLength > 0){
            char[] buffer = new char[contentLength];
            is.read(buffer, 0, contentLength);
            String body = String.valueOf(buffer);
            return HttpMessage.parse(startLine, header, body);
        }
        return null;
    }
}
