package actions.home;

import actions.auth.AuthAction;
import actions.auth.Login;
import actions.auth.Register;
import http.HttpReceiver;
import http.HttpSender;
import models.Auth;
import utils.Scanner;

public abstract class HomeAction {
    public abstract void perform(Auth auth, HttpSender sender, HttpReceiver receiver);

    public static HomeAction getAction() {
        System.out.println("Home");
        System.out.println("(1) List friends");
        System.out.println("(2) Add a friend");
        System.out.println("(3) Check account info");
        while (true) {
            try {
                int command = Integer.parseInt(Scanner.instance.nextLine());
                if (command == 1) {
                    return new ListFriend();
                }
                if (command == 2) {
                    return new AddFriend();
                }
                if (command == 3) {
                    return new CheckUserInfo();
                }
            } catch (Exception ignored) {
            }
        }
    }
}
