package actions.home;

import actions.PathAction;

import java.util.Stack;

public class GoToChatroomMenu extends PathAction {
    public void perform(Stack<String> pathStack) {
        pathStack.push("chatroom");
    }
}
