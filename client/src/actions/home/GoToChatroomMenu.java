package actions.home;

import actions.StateAction;
import http.HttpReceiver;
import http.HttpSender;
import models.Auth;
import state.ClientState;

public class GoToChatroomMenu extends StateAction {
    public void perform(ClientState state, Auth auth, HttpSender sender, HttpReceiver receiver) {
        state.pathStack.push("chatroomMenu");
    }
}
