package actions.home;

import actions.VoidAction;
import http.HttpReceiver;
import http.HttpSender;
import models.Auth;

public class CheckUserInfo extends VoidAction {
    public void perform(Auth auth, HttpSender sender, HttpReceiver receiver) {
        System.out.println("Your account:");
        System.out.println("Username: " + auth.user.username);
        System.out.println("Password: " + auth.user.password);
        System.out.println("DisplayName: " + auth.user.displayName);
        System.out.println("");
    }
}
