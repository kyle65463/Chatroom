package actions;

import http.HttpReceiver;
import http.HttpSender;
import models.Auth;

public abstract class VoidAction extends Action {
    public abstract void perform(Auth auth, HttpSender sender, HttpReceiver receiver);
}
