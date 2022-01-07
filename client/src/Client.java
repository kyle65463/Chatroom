import actions.auth.Login;
import http.HttpReceiver;
import http.HttpSender;
import utils.Scanner;

import java.io.*;
import java.net.*;

public class Client {
    public static void run(String serverIp, int port) {
        try {
            InetAddress address = InetAddress.getLocalHost();
            Socket socket = new Socket(serverIp, port);
            HttpSender sender = new HttpSender(socket);
            HttpReceiver receiver = new HttpReceiver(socket);
            System.out.println("Client address: " + address);

            try {
                Login login = new Login();
                String authToken = login.perform(sender, receiver);
                while(authToken == null) {
                    System.out.println("Login failed");
                    authToken = login.perform(sender, receiver);
                }
                System.out.println(authToken);
            } catch (Exception e) {
                System.out.println("Socket read Error");
                e.printStackTrace();
            }
        } catch (IOException e) {
            System.out.print("IO Exception");
            e.printStackTrace();
        }
    }
}