import database.Firestore;

public class Main {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("usage: ./server <port>");
            return;
        }
        int port = Integer.parseInt(args[0]);
        Firestore firestore = new Firestore();
        firestore.getDoc();
        Server.run(port);
    }
}
