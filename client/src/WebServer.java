import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.util.Set;

public class WebServer extends WebSocketServer {

//    private static int TCP_PORT=12200;

//    private Set<WebSocket> conns;
    public PrintWriter ToServer;
    public WebSocket web;



    public WebServer(PrintWriter pw,boolean valid,int port) throws IOException {
        super(new InetSocketAddress(port));
//        super(new InetSocketAddress((valid)?port:TCP_PORT));
        System.out.println("Client Web port = " + port);
        ToServer = pw;
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