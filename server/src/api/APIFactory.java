package api;

import api.auth.*;
import api.chat.ReceiveMessageAPI;
import api.chatroom.AddUserToChatroomAPI;
import api.chatroom.CreateChatroomAPI;
import api.chatroom.DeleteUserFromChatroomAPI;
import api.chatroom.ListChatroomAPI;
import api.friend.AddFriendAPI;
import api.friend.DeleteFriendAPI;
import api.friend.ListFriendAPI;

import java.util.Arrays;
import java.util.List;

public class APIFactory {
    public static API getAPI(String path) {
        for(API api : apis) {
            if(api.getPath().compareTo(path) == 0) {
                return api;
            }
        }
        return new NotFound();
    }

    private static final List<API> apis = Arrays.asList(
            new LoginAPI(), new RegisterAPI(),
            new AddFriendAPI(), new DeleteFriendAPI(), new ListFriendAPI(),
            new AddUserToChatroomAPI(), new DeleteUserFromChatroomAPI(), new ListChatroomAPI(), new CreateChatroomAPI(),
            new ReceiveMessageAPI(),
            new NotFound()
    );
}
