package state;

import java.util.Stack;

public class ClientState {
    public Stack<String> pathStack = new Stack<String>();
    public String chatroomId = "";

    public void resetPath() {
        pathStack.clear();
        pathStack.push("home");
    }
}
