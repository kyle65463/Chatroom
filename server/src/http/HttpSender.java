package http;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class HttpSender {
    public HttpSender(Socket socket) throws IOException {
        this.os = new PrintWriter(socket.getOutputStream());
    }

    private final PrintWriter os;

    public void post(String path, String body) {
        String header = "POST " + path + " HTTP/1.1\r\n"
                + "Host:localhost\r\nContent-Length: " + body.length() + "\r\n" + "\r\n";
        os.print(header + body);
        os.flush();
    }

    public void response(int status, String body) {
        String message = status == 200 ? "OK" : status == 400 ? "Bad Request" : "Server Error";
        String header = "HTTP/1.1 " + status + " " + message +"/1.1\r\n"
                + "Host:localhost\r\nContent-Length: " + body.length() + "\r\n" + "\r\n";
        os.print(header + body);
        os.flush();
    }

    public void close() {
        os.close();
    }
}
