package http;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;

public class HttpSender {
    public HttpSender(Socket socket) throws IOException {
        this.os = new PrintWriter(socket.getOutputStream());
    }

    private final PrintWriter os;

    public void get(String path, String body, String authToken) {
        request(path, body, "GET", authToken);
    }

    public void delete(String path, String body, String authToken) {
        request(path, body, "DELETE", authToken);
    }

    public void post(String path, String body, String authToken) {
        request(path, body, "POST", authToken);
    }

    public void post(String path, String body) {
        request(path, body, "POST", "");
    }

    public static HttpRequest makeRequest(String path, String body, String requestType) {
        return new HttpRequest(new HashMap<>(), body, requestType, path);
    }

    private void request(String path, String body, String requestType, String authToken) {
        String header = requestType + " " + path + " HTTP/1.1\r\n" +
                        "Host:localhost\r\n" +
                        "Content-Length: " + body.length() + "\r\n" +
                        (authToken.length() > 0 ? "Authorization: " + authToken + "\r\n" : "") +
                        "\r\n";
        os.print(header + body);
        os.flush();
    }

    public void response(int status,String path, String body) {
        String message = status == 200 ? "OK" : status == 400 ? "Bad Request" : "Server Error";
        String header = "HTTP/1.1 " + status + " " + message +"/1.1\r\n" + "Path: "+ path + "\r\n"
                + "Host:localhost\r\nContent-Length: " + body.length() + "\r\n" + "\r\n";
        os.print(header + body);
        os.flush();
    }

    public void close() {
        os.close();
    }
}
