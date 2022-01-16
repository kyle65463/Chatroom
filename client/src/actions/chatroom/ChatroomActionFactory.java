package actions.chatroom;

import actions.Action;
import http.HttpReceiver;
import utils.Scanner;

public abstract class ChatroomActionFactory {
    public static Action getAction(HttpReceiver receiver) {
        while (true) {
            System.out.println("(1) Send text message");
            System.out.println("(2) Send file");
            System.out.println("(3) Get chat histories");
            try {
                if(Scanner.instance.hasNext()) {
                    int command = Integer.parseInt(Scanner.instance.nextLine());
                    if (command == 1) {
                        return new SendTextMessage();
                    }
                    if (command == 2) {
                        return new SendFileMessage();
                    }
                    if (command == 3) {
                        return new GetChatHistories();
                    }
                }
            } catch (Exception ignored) {
            }
            System.out.println("Invalid action.");
        }
    }
}
