import database.Firestore;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.out.println("usage: ./server <port>");
            return;
        }
        int port = Integer.parseInt(args[0]);
        Firestore.init();
        Server.run(port);
    }
}
