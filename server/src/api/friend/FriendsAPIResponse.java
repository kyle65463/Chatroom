package api.friend;

import java.util.List;

public class FriendsAPIResponse {
    public FriendsAPIResponse(List<String> friendIds) {
        this.friendIds = friendIds;
    }
    public final List<String> friendIds;
}
