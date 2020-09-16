import MyNewMap.NewHashMap;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runners.Parameterized;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class Tests {
    public static Map<String, Integer> newMap = new NewHashMap<>();
    public static Map<String, Integer> originalMap = new HashMap<>();

    @Before
    public void fill(){
        for (int i = 0; i <= 15000; i++) {
            newMap.put(String.valueOf(i), i);
        }

        for (int i = 15000; i >=0; i--) {
            originalMap.put(String.valueOf(i), i);
        }
    }

    @Test
    public void putForMaps() {
        String key  = "50";
        int value = 100;
        Assert.assertTrue(((NewHashMap<String, Integer>)newMap).equals(originalMap));
        Assert.assertEquals(originalMap.containsValue(value), newMap.containsValue(value));
        assertEquals(originalMap.get(key), newMap.get(key));
    }

    @Test(expected = IllegalArgumentException.class)
    public void createMapWithCapacity() {
        newMap = new NewHashMap<>(-10);

    }

    @Test(expected = IllegalArgumentException.class)
    public void updateObject() {
        newMap.replace("-1--", 150);
    }

    @Test
    public void checkKey() {
        String key = "50";
        assertEquals(originalMap.get(key), newMap.get(key));
        assertEquals(originalMap.size(), newMap.size());
    }

    @Test
    public void checkDeleteObject() {
        String removeString = "10";
        assertEquals(newMap.remove(removeString), originalMap.remove(removeString));
        assertEquals(newMap.size(), originalMap.size());
    }

    @Test
    public void checkContainsValue(){
        int value = 20;
        assertEquals(newMap.containsValue(value), originalMap.containsValue(value));
        assertTrue(newMap.containsValue(value));
    }

    @Test
    public void checkContainsKey(){
        String key = "-100";
        assertEquals(newMap.containsKey(key), originalMap.containsKey(key));
    }

    @Test(expected = IllegalArgumentException.class)
    public void notFindKey(){
        int number = 100;
        newMap.get(number);
    }
}