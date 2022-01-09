package actions.chatroomMenu;

import actions.Action;
import actions.GoBack;
import actions.auth.Logout;
import actions.home.AddFriend;
import actions.home.CheckUserInfo;
import actions.home.DeleteFriend;
import actions.home.ListFriend;
import utils.Scanner;

public abstract class ChatroomMenuActionFactory {
    public static Action getAction() {
        while (true) {
            System.out.println("Chat room menu:");
            System.out.println("(1) List chat rooms");
            System.out.println("(2) Create a chat room");
            System.out.println("(3) Join to an existing chat room");
            System.out.println("(4) Choose a chat room to chat");
            System.out.println("(5) Back");
            try {
                int command = Integer.parseInt(Scanner.instance.nextLine());
                if (command == 1) {
                    return new ListChatroom();
                }
                if (command == 2) {
                    return new CreateChatroom();
                }
                if (command == 3) {
                    return new DeleteFriend();
                }
                if (command == 4) {
                    return new CheckUserInfo();
                }
                if (command == 5) {
                    return new GoBack();
                }
            } catch (Exception ignored) {
            }
            System.out.println("Invalid action.");
        }
    }
}
