package database;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.*;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import models.Chatroom;
import models.Friend;
import models.User;
import utils.JsonUtils;

import java.io.IOException;
import java.util.*;

public class Firestore extends Database {
    public static void init() throws IOException {
        GoogleCredentials credentials = GoogleCredentials.getApplicationDefault();
        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(credentials)
                .build();
        FirebaseApp.initializeApp(options);
        db = FirestoreClient.getFirestore();
    }

    public User createUser(String displayName, String username, String password) throws Exception {
        // Check if user exists
        Query query = db.collection("users").whereEqualTo("username", username);
        if(query.get().get().getDocuments().size() > 0) throw new Exception("Username exists.");

        // Create the user
        try {
            Map<String, Object> userData = new HashMap<>();
            userData.put("displayName", displayName);
            userData.put("username", username);
            userData.put("password", password);
            userData.put("friendIds", new ArrayList<>());
            userData.put("chatroomIds", new ArrayList<>());
            ApiFuture<WriteResult> future = db.collection("users").document(username).set(userData);
            future.get();
            return new User(username, displayName, password, new ArrayList<>(), new ArrayList<>());
        }
        catch (Exception e) {
            throw new Exception("Create user error.");
        }
    }

    // For login
    public User getUser(String username, String password) throws Exception  {
        try {
            Query query = db.collection("users")
                    .whereEqualTo("username", username)
                    .whereEqualTo("password", password);
            QuerySnapshot querySnapshot = query.get().get();
            List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
            if(documents.size() > 0) {
                QueryDocumentSnapshot doc = documents.get(0);
                String displayName = doc.getString("displayName");
                List<String> friendIds = (List<String>) doc.get("friendIds");
                List<String> chatroomIds = (List<String>) doc.get("chatroomIds");
                return new User(username, displayName, password, friendIds, chatroomIds);
            }
        }
        catch (Exception e) {
            throw new Exception("Login error.");
        }

        // User not found
        throw new Exception("Username or password incorrect.");
    }

    public User getUser(String username) throws Exception  {
        try {
            Query query = db.collection("users").whereEqualTo("username", username);
            QuerySnapshot querySnapshot = query.get().get();
            List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
            if(documents.size() > 0) {
                QueryDocumentSnapshot doc = documents.get(0);
                String password = doc.getString("password");
                String displayName = doc.getString("displayName");
                List<String> friendIds = (List<String>) doc.get("friendIds");
                List<String> chatroomIds = (List<String>) doc.get("chatroomIds");
                return new User(username, displayName, password, friendIds, chatroomIds);
            }
        }
        catch (Exception e) {
            throw new Exception("Get user error.");
        }

        // User not found
        throw new Exception("User not found.");
    }

    public List<Friend> getFriends(List<String> ids) throws Exception {
        try {
            List<Friend> friends = new ArrayList<>();
            if(ids.size() <= 0) return friends;
            Query query = db.collection("users").whereIn("username", ids);
            QuerySnapshot querySnapshot = query.get().get();
            List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
            for (QueryDocumentSnapshot doc : documents) {
                String displayName = doc.getString("displayName");
                String username = doc.getString("username");
                friends.add(new Friend(username, displayName));
            }
            return friends;
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Get friend list error.");
        }
    }

    public void updateUser(User user) throws Exception {
        System.out.println(user);
        System.out.println(JsonUtils.toJson(user));
        ApiFuture<WriteResult> result = db.collection("users").document(user.username).set(user);
        result.get();
    }

    public Chatroom createChatroom(String chatroomName, String username) throws Exception {
        try {
            Map<String, Object> chatroomData = new HashMap<>();
            List<String> usernames = Collections.singletonList(username);
            String id = UUID.randomUUID().toString();
            chatroomData.put("id", id);
            chatroomData.put("name", chatroomName);
            chatroomData.put("usernames", usernames);
            ApiFuture<WriteResult> future = db.collection("chatrooms").document(id).set(chatroomData);
            future.get();
            return new Chatroom(id, chatroomName, usernames);
        }
        catch (Exception e) {
            throw new Exception("Create chatroom error.");
        }
    }

    public Chatroom getChatroom(String id) throws Exception  {
        try {
            Query query = db.collection("chatrooms").whereEqualTo("id", id);
            QuerySnapshot querySnapshot = query.get().get();
            List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
            if(documents.size() > 0) {
                QueryDocumentSnapshot doc = documents.get(0);
                String name = doc.getString("name");
                List<String> usernames = (List<String>) doc.get("usernames");
                return new Chatroom(id, name, usernames);
            }
        }
        catch (Exception e) {
            throw new Exception("Get user error.");
        }

        // User not found
        throw new Exception("User not found.");
    }

    public void updateChatroom(Chatroom chatroom) throws Exception {
        ApiFuture<WriteResult> result = db.collection("chatrooms").document(chatroom.id).set(chatroom);
        result.get();
    }

    private static com.google.cloud.firestore.Firestore db;
}
