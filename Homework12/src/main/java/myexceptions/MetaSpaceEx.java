package myexceptions;

import javassist.CannotCompileException;
import javassist.ClassPool;

public class MetaSpaceEx implements MyOutOfMemoryEx{

    /**
     * Class pool for make classes for throw MetaSpaceException.
     */
    private static ClassPool classPool = ClassPool.getDefault();

    /**
     * Make more classes for throw OutOfMemoryError
     * @throws CannotCompileException
     */
    @Override
    public void doException() throws CannotCompileException {
        for (int i = 0; i < 1000000; i++) {
            Class clas = classPool.makeClass(
                    i + " outofmemory.OutOfMemoryErrorMetaspace ").toClass();
            System.out.println(clas.getName());
        }
    }
}
