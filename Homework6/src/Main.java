import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        Server server = new Server("localhost", 8080);
        try {
            server.start();
        } catch (IOException e) {
            System.out.println("Server connect failed");
        }
    }
}
