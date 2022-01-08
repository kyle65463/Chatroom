package actions.home;

import http.HttpReceiver;
import http.HttpSender;
import models.Auth;
import models.Friend;


public class ListFriend extends HomeAction {
    public void perform(Auth auth, HttpSender sender, HttpReceiver receiver) {
        System.out.println("Friends:");
        if(auth.user.friends.size() == 0) {
            System.out.println("No friends");
        }
        for(Friend friend : auth.user.friends) {
            System.out.println(friend.displayName + "(" + friend.username + ")");
        }
        System.out.println("");
    }
}
