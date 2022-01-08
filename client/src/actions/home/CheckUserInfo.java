package actions.home;

import http.HttpMessage;
import http.HttpReceiver;
import http.HttpResponse;
import http.HttpSender;
import models.Auth;
import utils.JsonUtils;
import utils.Scanner;

import java.util.HashMap;
import java.util.Map;

public class CheckUserInfo extends HomeAction {
    public void perform(Auth auth, HttpSender sender, HttpReceiver receiver) {
        System.out.println("Your account:");
        System.out.println("Username: " + auth.user.username);
        System.out.println("Password: " + auth.user.password);
        System.out.println("DisplayName: " + auth.user.displayName);
        System.out.println("");
    }
}
