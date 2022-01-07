import http.HttpReceiver;
import http.HttpSender;

import java.io.*;
import java.net.*;

public class Client {
    public static void run(String serverIp, int port) {
        try {
            InetAddress address = InetAddress.getLocalHost();
            Socket socket = new Socket(serverIp, port); // You can use static final constant PORT_NUM
            BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
            HttpSender sender = new HttpSender(socket);
            HttpReceiver receiver = new HttpReceiver(socket);

            System.out.println("Client address: " + address);
            System.out.println("Enter data to echo Server (Enter QUIT to end):");

            try {
                String line = stdin.readLine();
                while (line.compareTo("QUIT") != 0) {
                    sender.post("/", line);
                    line = stdin.readLine();
                }
            } catch (IOException e) {
                System.out.println("Socket read Error");
                e.printStackTrace();
            }
        } catch (IOException e) {
            System.out.print("IO Exception");
            e.printStackTrace();
        }
    }
}