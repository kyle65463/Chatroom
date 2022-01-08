import actions.Action;
import actions.auth.Login;
import actions.auth.Register;
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
                String authToken = null;
                while(authToken == null) {
                    System.out.println("(1) Login");
                    System.out.println("(2) Register");
                    int command = Integer.parseInt(Scanner.instance.nextLine());

                    Action action = new Login();
                    if(command == 2) {
                        action = new Register();
                    }
                    authToken = action.perform(sender, receiver);
                    if(authToken == null) {
                        System.out.println("Try again");
                    }
                }
                System.out.println("Successful");
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