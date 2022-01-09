import actions.Action;
import actions.PathAction;
import actions.VoidAction;
import actions.auth.AuthAction;
import actions.chatroomMenu.ChatroomMenuActionFactory;
import actions.home.HomeActionFactory;
import http.HttpReceiver;
import http.HttpSender;
import models.Auth;

import java.io.*;
import java.net.*;
import java.util.Stack;

public class Client {
    public static void run(String serverIp, int port) {
        try {
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
                            System.out.println("Welcome, " + auth.user.displayName + "!");
                            pathStack.clear();
                            pathStack.push("home");
                        }
                    }

                    String path = "/" + String.join("/", pathStack);
                    Action action = null;
                    if(path.compareTo("/home") == 0) {
                        action = HomeActionFactory.getAction();
                    }
                    if(path.compareTo("/home/chatroom") == 0) {
                        action = ChatroomMenuActionFactory.getAction();
                    }

                    if(action instanceof VoidAction voidAction) {
                        voidAction.perform(auth, sender, receiver);
                    }
                    else if(action instanceof PathAction pathAction) {
                        pathAction.perform(pathStack, auth, sender, receiver);
                    }
                    else if(action instanceof AuthAction authAction) {
                        auth = authAction.perform(sender, receiver);
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