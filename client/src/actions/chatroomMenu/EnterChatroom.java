package actions.chatroomMenu;

import actions.PathAction;
import actions.VoidAction;
import http.HttpMessage;
import http.HttpReceiver;
import http.HttpResponse;
import http.HttpSender;
import models.Auth;
import models.Chatroom;
import utils.JsonUtils;
import utils.Scanner;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class EnterChatroom extends PathAction {
    public void perform(Stack<String> pathStack, Auth auth, HttpSender sender, HttpReceiver receiver) {
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
                System.out.println("");
                pathStack.push("chatroom");
                return;
            } catch (Exception ignored) {

            }
            System.out.println("Invalid index.");
        }

    }
}
