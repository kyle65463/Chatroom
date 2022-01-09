package actions.auth;

import http.HttpReceiver;
import http.HttpSender;
import models.Auth;

public class Logout extends AuthAction {
    public Auth perform(HttpSender sender, HttpReceiver receiver) {
        // Set auth to null
        return null;
    }
}
