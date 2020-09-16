package MyNewMap;

import java.lang.reflect.Method;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Map<String, String> map = new NewHashMap<>();
        for (int i = 0; i < 15000; i++) {
            String put = map.put(i + "hello", String.valueOf(i));
            if (put != null) {
                System.out.println(put);
            }
        }
    }
}
