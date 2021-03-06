import org.junit.Assert;
import org.junit.Test;

public class MyMapTest {
    MyMap myMap = new MyMap();

    @Test
    public void putForMap() {
        myMap.put(123, 124);
        Assert.assertEquals(1, myMap.size());
    }

    @Test (expected = IllegalArgumentException.class)
    public void createMapWithCapacity(){
        myMap = new MyMap(-100);
    }

    @Test (expected = IllegalArgumentException.class)
    public void updateObject(){
        myMap.update(123,123);
    }

    @Test
    public void checkKey(){
        myMap.put(123,123);
        Assert.assertTrue(myMap.containsKey(123));
    }

    @Test
    public void checkDeleteObject(){
        myMap = new MyMap();
        for (int i = 0; i < 100; i++) {
            myMap.put(i, i);
        }
        myMap.delete(15);
        myMap.delete(12);
        myMap.delete(55);
        myMap.delete(16);
        myMap.delete(85);
        Assert.assertEquals(95, myMap.size());
        System.out.println(myMap);
    }
}
