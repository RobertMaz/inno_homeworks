import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpServer;
import javax.xml.ws.http.HTTPException;
import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

public class Server implements ServerHandler{
    final private String ADDRESS;
    final private int PORT;
    final private String ACCEPT = "\nAll ok";
    final private String ERROR_TEXT = "\nError 404. File not found.";
    final private String EXPECTED_REQUEST = "GET";

    /**
     * Constructor for Server. Need enter ipAddress and Port
     * @param address - ipAddress
     * @param port
     */
    public Server(String address, int port) {
        this.ADDRESS = address;
        this.PORT = port;
    }

    /**
     * Server start. And handler start. If request equals GET,
     * then print all files in entered URI directory
     * @throws IOException if server connect failed
     */
    public void start() throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(ADDRESS, PORT), 0);
        HttpContext context = server.createContext("/");
        context.setHandler(exchange -> {

            String currentRequest = exchange.getRequestMethod();
            String enteredFileName = exchange.getRequestURI().toString().substring(1);

            if (EXPECTED_REQUEST.equals(currentRequest) && Files.isDirectory(Paths.get(enteredFileName))) {
                exchange.sendResponseHeaders(200, ACCEPT.getBytes().length);
                exchange.getResponseBody().write(ACCEPT.getBytes());
                printFiles(enteredFileName);
            } else {
                System.out.println("Exception");
                exchange.sendResponseHeaders(404, ERROR_TEXT.getBytes().length);
                exchange.getResponseBody().write(ERROR_TEXT.getBytes());
                throw new HTTPException(404);
            }
        });

        server.start();

    }

    /**
     * Print files and directories in enter file.
     * @param name
     */
    private void printFiles(String name) {
        File file = new File(name);
        for (File listFile : Objects.requireNonNull(file.listFiles())) {
            System.out.println((listFile.isFile() ? "File : " : "Directory : ") + listFile.getName());
        }
    }
}
