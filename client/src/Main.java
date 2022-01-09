import utils.Scanner;

public class Main {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("usage: ./client <serverIp> <port>");
            return;
        }
        String serverIp = args[0];
        int port = Integer.parseInt(args[1]);
        Scanner.instance.nextLine();
        Client.run(serverIp, port);
    }
}
