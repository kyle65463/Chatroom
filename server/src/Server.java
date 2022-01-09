import api.API;
import api.ChatMessageAPI;
import api.HttpAPI;
import api.APIFactory;
import com.google.firebase.messaging.Message;
import database.Database;
import database.Firestore;
import http.HttpMessage;
import http.HttpReceiver;
import http.HttpRequest;
import http.HttpSender;

import java.net.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Server {
    public static void run(int port) {
        try {
            System.out.println("Listening on port " + port);
            ServerSocket serverSocket = new ServerSocket(port);
            while (true) {
                try {
                    Socket socket = serverSocket.accept();
                    ServerThread st = new ServerThread(socket);
                    st.start();
                    System.out.println("Connected");
                } catch (Exception e) {
                    System.out.println("Connection Error");
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            System.out.println("Server error");
            e.printStackTrace();
        }
    }
}

class ServerThread extends Thread {
    private final Socket socket;
    BlockingQueue<String> messageQueue = new LinkedBlockingQueue<String>();

    public ServerThread(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            Database database = new Firestore();
            HttpSender sender = new HttpSender(socket);
            HttpReceiver receiver = new HttpReceiver(socket);
            System.out.println(getName());
            while(true) {
                String msg;
                if((msg = messageQueue.peek()) != null) {
                    if(msg.compareTo(getName()) == 0) {
                        System.out.println(msg);
                        messageQueue.poll();
                    }
                }

                if(receiver.isReady()) {
                    HttpMessage message = receiver.readMessage();
                    System.out.println("Get request");
                    if (message instanceof HttpRequest request) {
                        API api = APIFactory.getAPI(request.path);
                        if(api instanceof HttpAPI httpAPI) {
                            httpAPI.handle(request, sender, database);
                        }
                        else if(api instanceof ChatMessageAPI chatMessageAPI) {
                            chatMessageAPI.handle(request, sender, database, messageQueue);
                        }

                        System.out.println("Send response");
                    } else {
                        System.out.println("Http error");
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Server thread error");
        }
    }
}