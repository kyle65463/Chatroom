package actions.auth;

import actions.Action;
import http.HttpReceiver;
import http.HttpSender;
import models.Auth;
import utils.Scanner;

public abstract class AuthAction extends Action {
    public abstract Auth perform(HttpSender sender, HttpReceiver receiver);

    public static AuthAction getCommand() {
        while (true) {
            System.out.println("Choose an action:");
            System.out.println("(1) Login");
            System.out.println("(2) Register");
            try {
                int command = Integer.parseInt(Scanner.instance.nextLine());
                if (command == 1) {
                    return new Login();
                }
                if (command == 2) {
                    return new Register();
                }
            } catch (Exception ignored) {
            }
            System.out.println("Invalid action.");
        }
    }
}
