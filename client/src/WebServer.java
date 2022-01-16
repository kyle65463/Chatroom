import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;

public class WebServer extends WebSocketServer {

    public PrintWriter ToServer;
    public WebSocket web;

    public WebServer(PrintWriter pw,boolean valid,int port) throws IOException {
        super(new InetSocketAddress(port));
        System.out.println("Client Web port = " + port);
        ToServer = pw;
    }
    public WebSocket ReturnWebSocket(){
        return web;
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        web = conn;
        System.out.println("New connection from " + conn.getRemoteSocketAddress().getAddress().getHostAddress());
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        System.out.println("Closed connection to " + conn.getRemoteSocketAddress().getAddress().getHostAddress());
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        System.out.println("Message from web\n" + message);
        ToServer.print(message);
        ToServer.flush();
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        System.out.println("ERROR from " + conn.getRemoteSocketAddress().getAddress().getHostAddress());
    }
}