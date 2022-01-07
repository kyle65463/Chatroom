package http;

import java.util.Map;

public class HttpRequest extends HttpMessage{
    public HttpRequest(Map<String, String> header, String body, String requestType, String path) {
        super(header, body);
        this.requestType = requestType;
        this.path = path;
    }

    public final String requestType;
    public final String path;
}
