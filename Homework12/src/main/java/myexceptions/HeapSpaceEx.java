package myexceptions;

public class HeapSpaceEx implements MyOutOfMemoryEx {

    /**
     * Concat words for OutOfMemoryError-HeapSpace
     */
    @Override
    public void doException() {
        String text = "asdf";
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            text += text;
            System.out.println(i);
        }
    }
}
