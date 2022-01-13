package actions;

import http.HttpReceiver;
import http.HttpSender;
import models.Auth;
import state.ClientState;

import java.util.Stack;

public class GoBack extends StateAction {
    public void perform(ClientState state, Auth auth, HttpSender sender, HttpReceiver receiver) {
        state.pathStack.pop();
    }
}
