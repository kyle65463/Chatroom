package actions;

import http.HttpReceiver;
import http.HttpSender;
import models.Auth;
import state.ClientState;

import java.util.Stack;

public abstract class StateAction extends Action {
    public abstract void perform(ClientState state, Auth auth, HttpSender sender, HttpReceiver receiver);
}
