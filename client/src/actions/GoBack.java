package actions;

import http.HttpReceiver;
import http.HttpSender;
import models.Auth;

import java.util.Stack;

public class GoBack extends PathAction {
    public void perform(Stack<String> pathStack, Auth auth, HttpSender sender, HttpReceiver receiver) {
        pathStack.pop();
    }
}
