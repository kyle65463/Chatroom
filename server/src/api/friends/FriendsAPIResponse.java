package api.friends;

import models.Friend;

import java.util.List;

public class FriendsAPIResponse {
    public FriendsAPIResponse(List<Friend> friends) {
        this.friends = friends;
    }
    public final List<Friend> friends;
}
