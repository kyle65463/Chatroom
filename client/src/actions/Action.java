package actions;

import http.HttpReceiver;
import http.HttpSender;

public abstract class Action {
    public abstract String perform(HttpSender sender, HttpReceiver receiver);
}
