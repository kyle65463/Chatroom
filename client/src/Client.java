import actions.Action;
import actions.auth.AuthAction;
import actions.auth.Login;
import actions.auth.Register;
import actions.home.HomeAction;
import http.HttpReceiver;
import http.HttpSender;
import models.Auth;
import models.Friend;
import utils.Scanner;

import java.io.*;
import java.net.*;
import java.util.Stack;

public class Client {
    public static void run(String serverIp, int port) {
        try {
            InetAddress address = InetAddress.getLocalHost();
            Socket socket = new Socket(serverIp, port);
            HttpSender sender = new HttpSender(socket);
            HttpReceiver receiver = new HttpReceiver(socket);
            System.out.println("Connected to server!");

            try {
                // Handle commands
                Auth auth = null;
                Stack<String> pathStack = new Stack<String>();
                while(true) {
                    if (auth == null) {
                        AuthAction action = AuthAction.getCommand();
                        auth = action.perform(sender, receiver);
                        if (auth == null) {
                            // Auth failed
                            System.out.println("Try again");
                        }
                        else {
                            // Default login to home page
                            System.out.println("");
                            System.out.println("Welcome, " + auth.user.displayName);
                            pathStack.clear();
                            pathStack.push("home");
                        }
                    }

                    String path = "/" + String.join("/", pathStack);
                    if(path.compareTo("/home") == 0) {
                        HomeAction action = HomeAction.getAction();
                        action.perform(auth, sender, receiver);
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}