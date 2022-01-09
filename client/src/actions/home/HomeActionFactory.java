package actions.home;

import actions.Action;
import actions.auth.Logout;
import utils.Scanner;

public abstract class HomeActionFactory {
    public static Action getAction() {
        while (true) {
            System.out.println("Home:");
            System.out.println("(1) List friends");
            System.out.println("(2) Add a friend");
            System.out.println("(3) Delete a friend");
            System.out.println("(4) Choose a chat room");
            System.out.println("(5) Check account info");
            System.out.println("(6) Logout");
            try {
                int command = Integer.parseInt(Scanner.instance.nextLine());
                if (command == 1) {
                    return new ListFriend();
                }
                if (command == 2) {
                    return new AddFriend();
                }
                if (command == 3) {
                    return new DeleteFriend();
                }
                if (command == 4) {
                    return new CheckUserInfo();
                }
                if (command == 5) {
                    return new CheckUserInfo();
                }
                if (command == 6) {
                    return new Logout();
                }
            } catch (Exception ignored) {
            }
            System.out.println("Invalid action.");
        }
    }
}
