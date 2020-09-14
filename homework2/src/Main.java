import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        MyMap myMap = new MyMap(16);
        for (int i = 0; i < 150; i++) {
            myMap.put(i, i);
        }
        myMap.put(17, "root");
        myMap.put("String", "world");
        myMap.update(1, "new Obj");
        System.out.println(myMap.size());
        System.out.println(myMap.delete(17));
        System.out.println(myMap.delete(145));
        System.out.println(myMap.getValue("String"));

        System.out.println(myMap);

    }
}
