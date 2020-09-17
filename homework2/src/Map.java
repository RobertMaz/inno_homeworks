public interface Map {
    void put(Object key, Object value);
    Object getValue(Object key);
    Object delete(Object key);
    Object update(Object key, Object value);
    int size();
    boolean containsKey(Object key);
}