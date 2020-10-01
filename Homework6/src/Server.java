import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Objects;

public class Server implements ServerHandler {
    final private String address;
    final private int port;
    final private static String EXPECTED_REQUEST = "GET";
    final private static String ERROR_TEXT = "\nError 404. File not found.";

    /**
     * Constructor for Server. Need enter ipAddress and Port
     *
     * @param address - ipAddress
     * @param port
     */
    public Server(String address, int port) {
        this.address = address;
        this.port = port;
    }

    /**
     * Server start. And handler start. If request equals GET,
     * then print all files in entered URI directory
     *
     * @throws IOException if server connect failed
     */
    public void start() throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(address, port), 0);
        HttpContext context = server.createContext("/");
        context.setHandler(exchange -> {
            String currentRequest = exchange.getRequestMethod();
            if (EXPECTED_REQUEST.equals(currentRequest)) {
                printFiles(exchange, "./");
            } else {
                System.out.println("Exception");
                exchange.sendResponseHeaders(404, ERROR_TEXT.getBytes().length);
                exchange.getResponseBody().write(ERROR_TEXT.getBytes());
            }
        });

        server.start();

    }

    /**
     * Print files and directories in current directory.
     * Print to client window.
     *
     * @param exchange
     * @param path
     */
    private void printFiles(HttpExchange exchange, String path) throws IOException {
        File file = new File(path);
        StringBuilder sb = new StringBuilder();
        for (File listFile : Objects.requireNonNull(file.listFiles())) {
            sb.append((listFile.isFile() ? "File : " : "Directory : ")).append(listFile.getName()).append("\n");
        }
        byte[] response = sb.toString().getBytes();
        exchange.sendResponseHeaders(200, response.length);
        exchange.getResponseBody().write(response);

    }

    public String getAddress() {
        return address;
    }

    public int getPort() {
        return port;
    }
}
