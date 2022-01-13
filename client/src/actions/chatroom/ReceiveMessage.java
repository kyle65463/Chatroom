package actions.chatroom;

import actions.StateAction;
import http.HttpMessage;
import http.HttpReceiver;
import http.HttpResponse;
import http.HttpSender;
import models.Auth;
import models.chat.TextMessage;
import state.ClientState;

public class ReceiveMessage extends StateAction {
    public void perform(ClientState state, Auth auth, HttpSender sender, HttpReceiver receiver) {
        try {
            HttpMessage message = receiver.readMessage();
            if(message instanceof HttpResponse response) {
                if(response.status == 200) {
                    String chatroomId = (String) response.body.get("chatroomId");
                    String messageId = (String) response.body.get("id");
                    String senderUsername = (String) response.body.get("sender");
                    String type = (String) response.body.get("type");

                    String output = "";
                    if(type.equals(TextMessage.getType())) {
                        output = (String) response.body.get("content");

                    }
                    else {
                        // File messages
                        String filename = (String) response.body.get("filename");
                        output = filename + "(" + messageId + ")";
                    }

                    System.out.println(senderUsername + ": " + output);
                }
                else {
                    // Request failed
                    System.out.println(response.body.get("error"));
                }
            }
        }
        catch (Exception ignored) {
            ignored.printStackTrace();
        }
    }
}
