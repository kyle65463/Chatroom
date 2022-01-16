import http.HttpMessage;
import http.HttpReceiver;
import http.HttpRequest;
import http.HttpSender;
import org.java_websocket.WebSocket;
import utils.Scanner;

import javax.naming.BinaryRefAddr;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("usage: ./client <serverIp> <port>");
            return;
        }
        String serverIp = args[0];
        int port = Integer.parseInt(args[1]);
        Scanner.instance.nextLine();

        Thread client = new Thread(new Runnable(){
            @Override
            public void run() {
//                String serverIp = args[0];
//                int port = Integer.parseInt(args[1]);
//                Scanner.instance.nextLine();
                Client.run(serverIp, port);
            }
        }, "Thread-client");


        Thread web = new Thread(new Runnable(){
            @Override
            public void run() {
                try{
                    ///Client <-> Server
                    Socket clientToServerSocket = new Socket(serverIp, port);
                    PrintWriter csSend = new PrintWriter(clientToServerSocket.getOutputStream());
                    BufferedReader csRead = new BufferedReader(new InputStreamReader(clientToServerSocket.getInputStream()));
                    ///Web <-> Client
                    WebServer webSocketServer = new WebServer(csSend,csRead);
                    webSocketServer.start();
                    WebSocket web = null;
                    while(web == null){
                        web = webSocketServer.ReturnWebSocket();
                    }

                    /// message exchange
                    while(true){
                        if(web.isClosed() || !web.isConnecting()){
                            web = null;
                            while(web == null){
                                web = webSocketServer.ReturnWebSocket();
                            }
                        }
                        if(csRead.ready()){
                            String message = "";
                            int value = 0;
                            while( (csRead.ready()) && ((value = csRead.read()) != -1)) {
                                char c = (char) value;
                                message += Character.toString(c);
                            }
                            System.out.println(message.toString());
                            if(!web.isClosed()) {
                                web.send(message.toString());
                            }
                        }
                    }


                }catch(IOException e) {
                    System.out.println("IOException");
                    e.printStackTrace();
                } catch (Exception e) {
                    System.out.println("Exception");
                    e.printStackTrace();
                }
            }
        }, "Thread-web");
        client.start();
        web.start();
    }
}
