package actions.chatroom;

import actions.Action;
import actions.GoBack;
import actions.chatroomMenu.CreateChatroom;
import actions.chatroomMenu.EnterChatroom;
import actions.chatroomMenu.JoinChatroom;
import actions.chatroomMenu.ListChatroom;
import http.HttpMessage;
import http.HttpReceiver;
import http.HttpResponse;
import utils.Scanner;

public abstract class ChatroomActionFactory {
    public static Action getAction(HttpReceiver receiver) {
        while (true) {
            System.out.println("(1) Send text message");
            System.out.println("(2) Receive");
            try {
                if(Scanner.instance.hasNext()) {
                    int command = Integer.parseInt(Scanner.instance.nextLine());
                    if (command == 1) {
                        return new SendTextMessage();
                    }
                    if (command == 2) {
                        HttpMessage message = receiver.readMessage();
                        if(message instanceof HttpResponse response) {
                            String content = (String) response.body.get("content");
                            System.out.println("receive: " + content);
                        }
                    }
                }
            } catch (Exception ignored) {
            }
            System.out.println("Invalid action.");
        }
    }
}
