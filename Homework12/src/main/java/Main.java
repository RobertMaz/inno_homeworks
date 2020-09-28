import javassist.CannotCompileException;
import myexceptions.HeapSpaceEx;
import myexceptions.MyOutOfMemoryEx;

public class Main {
    public static void main(String[] args) throws CannotCompileException {


        MyOutOfMemoryEx metaSpaceEx = new HeapSpaceEx();
        metaSpaceEx.doException();
    }
}
