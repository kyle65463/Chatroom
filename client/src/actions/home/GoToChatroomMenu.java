package actions.home;

import actions.PathAction;
import http.HttpReceiver;
import http.HttpSender;
import models.Auth;

import java.util.Stack;

public class GoToChatroomMenu extends PathAction {
    public void perform(Stack<String> pathStack, Auth auth, HttpSender sender, HttpReceiver receiver) {
        pathStack.push("chatroom");
    }
}
