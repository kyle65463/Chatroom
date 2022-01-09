package actions;

import java.util.Stack;

public class GoBack extends PathAction {
    public void perform(Stack<String> pathStack) {
        pathStack.pop();
    }
}
