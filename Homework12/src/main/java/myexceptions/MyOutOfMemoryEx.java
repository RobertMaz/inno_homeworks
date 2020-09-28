package myexceptions;

import javassist.CannotCompileException;

public interface MyOutOfMemoryEx {

    /**
     * Generate out of memory exception.
     * @throws CannotCompileException
     */
    void doException() throws CannotCompileException;
}
