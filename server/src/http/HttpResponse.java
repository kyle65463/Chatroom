package http;

import java.util.Map;

public class HttpResponse extends HttpMessage{
    public HttpResponse(Map<String, String> header, String body, int status) {
        super(header, body);
        this.status = status;
    }

    public final int status;
}
