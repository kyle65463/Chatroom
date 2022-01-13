package actions.chatroomMenu;

import actions.StateAction;
import http.HttpReceiver;
import http.HttpSender;
import models.Auth;
import models.Chatroom;
import state.ClientState;
import utils.Scanner;

import java.util.List;
import java.util.Stack;

public class EnterChatroom extends StateAction {
    public void perform(ClientState state, Auth auth, HttpSender sender, HttpReceiver receiver) {
        List<Chatroom> chatrooms = new ListChatroom(true).getChatroomList(auth, sender, receiver);
        if (chatrooms.size() == 0) return;
        if (chatrooms.size() == 1) {
            System.out.println("Choose a chat room:");
        }
        else {
            System.out.printf("Choose a chat room (%d - %d):%n", 1, chatrooms.size());
        }

        while (true) {
            try {
                int index = Integer.parseInt(Scanner.instance.nextLine()) - 1;
                state.pathStack.push("chatroom");
                state.chatroomId = chatrooms.get(index).id;
                System.out.println("");
                return;
            } catch (Exception ignored) {

            }
            System.out.println("Invalid index.");
        }

    }
}
