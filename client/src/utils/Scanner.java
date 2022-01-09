package utils;

public class Scanner {
    public static final java.util.Scanner instance = new java.util.Scanner(System.in);

    public static String getRequiredData(String dataName) {
        String data = Scanner.instance.nextLine();
        while(data.trim().length() == 0) {
            System.out.println(dataName + " is required.");
            data = Scanner.instance.nextLine();
        }
        return data.trim();
    }
}
