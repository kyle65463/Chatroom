package actions;

import http.HttpReceiver;
import http.HttpSender;
import models.Auth;

import java.util.Stack;

public abstract class PathAction extends Action {
    public abstract void perform(Stack<String> pathStack, Auth auth, HttpSender sender, HttpReceiver receiver);
}
