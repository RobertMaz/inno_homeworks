import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Test {
    private static final String FILE_NAME = "SomeClass";

    public static void main(String[] args)  {
        ClassBuilder classBuilder = new ClassBuilder(FILE_NAME);
        try {
            classBuilder.build();
            classBuilder.compile(null, System.out, System.err);

            MyClassLoader myClassLoader = new MyClassLoader(FILE_NAME);
            Class<?> aClass = myClassLoader.findClass(FILE_NAME);

            Object o = aClass.getConstructors()[0].newInstance();
            Method method = aClass.getMethods()[0];
            method.invoke(o);

        } catch (IOException e) {
            System.out.println("Problem with file" + e);
        } catch (InstantiationException | ClassNotFoundException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }


    }
}
