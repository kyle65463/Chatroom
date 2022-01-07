package http;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public abstract class HttpMessage {
    protected HttpMessage(Map<String, String> header, String body) {
        this.header = header;
        this.body = body;
    }

    public static HttpMessage parse(String startLine, Map<String, String> header, String body) {
        List<String> tokens = Arrays.asList(startLine.split(" ", 3));
        if(tokens.size() == 3) {
            if(requestTypes.contains(tokens.get(0))) {
                // A request, e.g. "POST /?id=1 HTTP/1.1"
                String requestType = tokens.get(0);
                String path = tokens.get(1);
                return new HttpRequest(header, body, requestType, path);
            }
            if(tokens.get(0).compareTo("HTTP/1.1") == 0) {
                // A response, e.g. "HTTP/1.1 200 OK"
                int status = Integer.parseInt(tokens.get(1));
                return new HttpResponse(header, body, status);
            }
        }
        return null;
    }

    public final Map<String, String> header;
    public final String body;
    private static final List<String> requestTypes = Arrays.asList("GET", "POST");
}
