import api.API;
import api.APIFactory;
import database.Database;
import database.Firestore;
import http.*;

import java.net.*;

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
    private final ThreadMessenger threadMessenger;

    public ServerThread(Socket socket) {
        this.socket = socket;
        this.threadMessenger = new ThreadMessenger(getName());
    }

    public void run() {
        try {
            Database database = new Firestore();
            HttpSender sender = new HttpSender(socket);
            HttpReceiver receiver = new HttpReceiver(socket);

            // TODO: Remove user entry in threadMessenger after user logged out or quited

            while(true) {
                if(threadMessenger.isReady()) {
                    // Handle chat message
                    HttpMessage message = threadMessenger.readMessage();
                    if (message instanceof HttpRequest request) {
                        API api = APIFactory.getAPI(request.path);
                        api.handle(request, sender, database, threadMessenger);
                    } else {
                        System.out.println("Http error");
                    }
                }

                if(receiver.isReady()) {
                    HttpMessage message = receiver.readMessage();
                    if (message instanceof HttpRequest request) {
                        System.out.println("Get request");
                        API api = APIFactory.getAPI(request.path);
                        api.handle(request, sender, database, threadMessenger);
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