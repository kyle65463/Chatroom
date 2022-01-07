package database;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.*;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import models.User;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Firestore extends Database {
    public static void init() throws IOException {
        GoogleCredentials credentials = GoogleCredentials.getApplicationDefault();
        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(credentials)
                .build();
        FirebaseApp.initializeApp(options);
        db = FirestoreClient.getFirestore();
    }

    public User createUser(String displayName, String username, String password) {
        try {
            Map<String, Object> userData = new HashMap<>();
            userData.put("displayName", displayName);
            userData.put("username", username);
            userData.put("password", password);
            ApiFuture<DocumentReference> future = db.collection("users").add(userData);
            DocumentReference doc = future.get();
            String id = doc.getId();
            return new User(id, username, displayName, password);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public User getUser(String username, String password)  {
        try {
            Query query = db.collection("users")
                    .whereEqualTo("username", username)
                    .whereEqualTo("password", password);
            QuerySnapshot querySnapshot = query.get().get();
            List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
            if(documents.size() > 0) {
                QueryDocumentSnapshot doc = documents.get(0);
                String id = doc.getId();
                String displayName = doc.getString("displayName");
                return new User(id, username, displayName, password);
            }
            return null;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static com.google.cloud.firestore.Firestore db;
}
