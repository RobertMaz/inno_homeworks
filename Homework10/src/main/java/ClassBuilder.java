import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.*;
import java.util.Scanner;

/**
 * Class build file .java. When use method input,
 * user enter code lines, and when line will be empty,
 * then with javac compiler we build file .class.
 */
public class ClassBuilder {
    private final String FILE_JAVA;

    /**
     * File name for use
     *
     * @param FILE_JAVA
     */
    public ClassBuilder(String FILE_JAVA) {
        this.FILE_JAVA = FILE_JAVA;
    }

    /**
     * First make file with name transferred parameters.
     * Class implementation, method name, wrote already.
     * Method body read with console.
     *
     * @throws IOException
     */
    public void build() throws IOException {
        Scanner scan = new Scanner(System.in);
        File file = new File(FILE_JAVA + ".java");
        try (BufferedWriter out = new BufferedWriter(new FileWriter(file))) {
            out.write("public class " + FILE_JAVA + " implements Worker {\n" +
                    "@Override \n" +
                    "public void doWork(){\n" +
                    "\n");
            System.out.println("Please enter doWork method code");

            while (true) {
                String code = scan.nextLine();
                out.write(code + "\n");
                if (code.length() < 1) {
                    break;
                }
            }

            scan.close();
            out.write("}\n" +
                    "}\n");
        }

    }

    /**
     * Compile this file.java
     * @param in
     * @param out
     * @param err
     */
    public void compile(InputStream in, OutputStream out, OutputStream err) {
        ToolProvider.getSystemJavaCompiler().run(in, out, err, FILE_JAVA + ".java");
    }
}
