import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Class loader with my implementation. Just class loader.
 */
public class MyClassLoader extends ClassLoader {
    private final String FILE_NAME;

    public MyClassLoader(String fileName) {
        this.FILE_NAME = fileName;
    }

    /**
     * Load class passed in parameters
     * @param name - file_name for find
     * @return
     * @throws ClassNotFoundException
     */
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        if (name.toLowerCase().contains(FILE_NAME.toLowerCase())) {
            try {
                final byte[] bytes = Files.readAllBytes(Paths.get(FILE_NAME + ".class"));
                return defineClass(null, bytes, 0, bytes.length);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        throw new ClassNotFoundException("Class not found");
    }
}
