import database.Database;
import database.Firestore;
import models.User;

import java.io.*;
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

    public ServerThread(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        Database database = new Firestore();
        try {
            BufferedReader is = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter os = new PrintWriter(socket.getOutputStream());
            try {
                String line = is.readLine();
                while (line.compareTo("QUIT") != 0) {
                    if(line.compareTo("create") == 0) {
                        os.println("enter username");
                        os.flush();
                        String username = is.readLine();
                        os.println("enter password");
                        os.flush();
                        String password = is.readLine();
                        os.println("enter display name");
                        os.flush();
                        String displayName = is.readLine();
                        User user = database.createUser(username, password, displayName);
                        if(user != null) {
                            os.println("create user successfully");
                            os.flush();
                        }
                        else {
                            os.println("create user failed");
                            os.flush();
                        }
                    }
                    os.println(line);
                    os.flush();
                    System.out.println("Response to Client  :  " + line);
                    line = is.readLine();
                }
            } catch (IOException e) {
                System.out.println("IO Error/ Client " + this.getName() + " terminated abruptly");
            } catch (NullPointerException e) {
                System.out.println("Client " + this.getName() + " Closed");
            } finally {
                try {
                    System.out.println("Closing connection...");
                    is.close();
                    os.close();
                    socket.close();
                } catch (IOException ie) {
                    System.out.println("Socket Close Error");
                }
            }
        } catch (Exception e) {
            System.out.println("Server thread error");
        }
    }
}