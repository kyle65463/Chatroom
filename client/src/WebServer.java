import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetSocketAddress;

public class WebServer extends WebSocketServer {

    private static int TCP_PORT = 12200;

//    private Set<WebSocket> conns;
    public PrintWriter ToServer;
    public BufferedReader FromServer;
    public WebSocket web;

    public WebServer(PrintWriter pw,BufferedReader br) throws IOException {
        super(new InetSocketAddress(TCP_PORT));
        ToServer = pw;
        FromServer = br;
//        conns = new HashSet<>();
    }
    public WebSocket ReturnWebSocket(){
        return web;
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
//        conns.add(conn);
        web = conn;
        System.out.println("New connection from " + conn.getRemoteSocketAddress().getAddress().getHostAddress());
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
//        conns.remove(conn);
        System.out.println("Closed connection to " + conn.getRemoteSocketAddress().getAddress().getHostAddress());
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        System.out.println("Message from client: " + message);
        ToServer.print(message);
        ToServer.flush();
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        //ex.printStackTrace();
        if (conn != null) {
//            conns.remove(conn);
            // do some thing if required
        }
        System.out.println("ERROR from " + conn.getRemoteSocketAddress().getAddress().getHostAddress());
    }
}