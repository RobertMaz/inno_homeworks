package MyNewMap;

import java.util.*;
import java.util.function.Consumer;


public class NewHashMap<K, V> implements Map<K, V> {

    /**
     * Elements table
     */
    private Node<K, V>[] table;

    /**
     * The default initial capacity - MUST be a power of two.
     */
    static final int DEFAULT_INITIAL_CAPACITY = 16;

    /**
     * The maximum capacity, used if a higher value is implicitly specified
     * by either of the constructors with arguments.
     * MUST be a power of two.
     */
    static final int MAXIMUM_CAPACITY = Integer.MAX_VALUE;

    /**
     * Table capacity
     */
    private int capacity;

    /**
     * Count all Objects in table
     */
    private int size;

    /**
     * Count not null elements table
     */
    private int bucketsCount;

    private Collection<V> values;

    private Set<K> keySet;

    private Set<Map.Entry<K, V>> entrySet;


    /**
     * Constructor
     *
     * @param capacity throws IllegalArgumentException, when capacity < 0
     */
    @SuppressWarnings("unchecked")
    public NewHashMap(int capacity) {
        if (capacity < 0) {
            throw new IllegalArgumentException("Illegal initial capacity: " + capacity);
        }

        this.capacity = capacity;
        table = new Node[capacity];
    }

    /**
     * Default constructor. Capacity initialized with default capacity 16
     */
    public NewHashMap() {
        this(DEFAULT_INITIAL_CAPACITY);
    }

    /**
     * @return all elements count
     */
    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean containsKey(Object key) {
        try {
            V value = get(key);
        } catch (IllegalArgumentException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean containsValue(Object value) {
        for (Map.Entry<K, V> pair : entrySet()) {
            if (pair != null && pair.getValue().equals(value)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Return value by K key
     *
     * @param key
     * @return V value
     */
    @Override
    public V get(Object key) {
        int hashCode = Math.abs(hash(key) % capacity);
        Node<K, V> current = table[hashCode];
        while (current != null) {
            if (current.getKey().equals(key)) {
                return current.getValue();
            } else {
                current = current.getNext();
            }
        }
        throw new IllegalArgumentException("Not find object with key=" + key);
    }

    /**
     * Method is put new element in table. If element by hashcode in table is not null,
     * then use putNodeByEqualsHash(newNode, bucket).
     *
     * @param key
     * @param value
     */
    @Override
    public V put(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException("Key can't be null");
        }

        if (bucketsCount >= capacity * 0.75) {
            increaseCapacity();
        }

        V oldV = null;
        Node<K, V> newNode = new Node<>(key, value);
        int hashCode = Math.abs(hash(key) % capacity);
        if (table[hashCode] == null) {
            table[hashCode] = newNode;
            size++;
            bucketsCount++;
        } else {
            oldV = putNodeByEqualsHash(newNode, hashCode);
        }

        return oldV;
    }

    /**
     * Method increase capacity in table.
     */
    @SuppressWarnings("unchecked")
    private void increaseCapacity() {
        bucketsCount = 0;
        size = 0;
        capacity *= 2;

        Node<K, V>[] oldTable = table;
        if (capacity >= MAXIMUM_CAPACITY - 1) {
            throw new ArrayIndexOutOfBoundsException("Array is full");
        }
        table = new Node[capacity];
        for (Node<K, V> node : oldTable) {
            while (node != null) {
                put(node.getKey(), node.getValue());
                node = node.next;
            }
        }
    }

    /**
     * Method put new Element in table, if hashcode is equals.
     * If table have key yet, then value by key is rewrite.     *
     *
     * @param newNode  is new element for table
     * @param hashCode hashcode element for table
     * @return oldValue if Value was rewrite, else null
     */
    private V putNodeByEqualsHash(Node<K, V> newNode, int hashCode) {
        Node<K, V> head = table[hashCode];
        V oldValue = null;
        while (head != null) {
            if (head.getKey().equals(newNode.getKey())) {
                oldValue = head.getValue();
                head.setValue(newNode.getValue());
                break;
            }
            if (head.getNext() == null) {
                head.setNext(newNode);
                size++;
                break;
            }
            head = head.getNext();
        }
        return oldValue;
    }

    /**
     * Method delete object in table by key, and return old Value.
     * If not find object, throw new IllegalArgumentException.
     *
     * @param key
     * @return oldValue by Key if key find, else throw new IllegalArgumentException.
     */
    @Override
    @SuppressWarnings("unchecked")
    public V remove(Object key) {
        Node<K, V> deleteNode = new Node(key, null);
        int currentHash = Math.abs(hash(key) % capacity);
        V oldValue = null;

        if (table[currentHash] == null) {
            throw new IllegalArgumentException("Not find object with key=" + key);
        }

        Node<K, V> head = table[currentHash];
        if (head.getNext() == null && head.getKey().equals(deleteNode.getKey())) {
            oldValue = table[currentHash].getValue();
            table[currentHash] = null;
            size--;
        } else {
            while (head.getNext() != null) {
                if (head.getKey().equals(deleteNode.getKey())) {
                    oldValue = head.getValue();
                    head.setCurrent(head.getNext());
                    size--;
                    break;
                }
                head = head.getNext();
            }
        }
        return oldValue;

    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        m.forEach(this::put);
    }

    /**
     * Clear table
     */
    @Override
    public void clear() {
        if (table != null && size > 0) {
            size = 0;
            Arrays.fill(table, null);
        }
    }

    /**
     * for compute hash object
     *
     * @param key
     * @return hashCode
     */
    static int hash(Object key) {
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h * 32);
    }

    /**
     * Method check all key and value in maps
     *
     * @param forEquals
     * @return true or false
     */
    public boolean equals(Map<K, V> forEquals) {
        if (forEquals.size() != this.size) {
            return false;
        }

        for (Map.Entry<K, V> pair : this.entrySet()) {
            if (!((pair == null) || (forEquals.get(pair.getKey()) == null))) {
                if (!forEquals.get(pair.getKey()).equals(pair.getValue())) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * @return a set view of the keys contained in this map
     */
    @Override
    public Set<K> keySet() {
        Set<K> es;
        return (es = keySet) != null ? es :
                (keySet = new AbstractSet<K>() {
                    @Override
                    public Iterator<K> iterator() {
                        return new KeyIterator();
                    }

                    @Override
                    public int size() {
                        return size;
                    }
                });
    }

    /**
     * @return a set view of the mappings contained in this map
     */
    @Override
    public Set<Entry<K, V>> entrySet() {
        Set<Entry<K, V>> es;
        return (es = entrySet) != null ? es :
                (entrySet = new AbstractSet<Entry<K, V>>() {
                    @Override
                    public Iterator<Entry<K, V>> iterator() {
                        return new EntryIterator();
                    }

                    @Override
                    public int size() {
                        return size;
                    }
                });
    }

    /**
     * Returns a view of the values contained in this map.
     *
     * @return a view of the values contained in this map
     */
    @Override
    public Collection<V> values() {
        Collection<V> vs = values;
        if (vs == null) {
            vs = new AbstractCollection<V>() {
                @Override
                public Iterator<V> iterator() {
                    return new ValueIterator();
                }

                @Override
                public int size() {
                    return size;
                }

                public final void forEach(Consumer<? super V> action) {
                    Node<K, V>[] tab;
                    if (action == null)
                        throw new NullPointerException();

                    if (size > 0 && (tab = table) != null) {
                        for (Node<K, V> kvNode : tab) {
                            for (Node<K, V> e = kvNode; e != null; e = e.next)
                                action.accept(e.getValue());
                        }
                    }
                }
            };
            values = vs;
        }
        return vs;
    }

    /**
     * Class for iterator
     */

    public abstract class AbstractNewIterator {
        private Node<K, V> next;
        private Node<K, V> current;

        private int index;

        AbstractNewIterator() {
            Node<K, V>[] t = table;
            current = next = null;
            index = 0;
            if (t != null && size > 0) {
                while (index < t.length && (next = t[index++]) == null) {
                }
            }
        }

        public boolean hasNext() {
            return next != null;
        }

        public Node<K, V> nextNode() {
            Node<K, V>[] t;
            Node<K, V> e = next;
            if (e == null)
                throw new NoSuchElementException();

            if ((next = (current = e).getNext()) == null && (t = table) != null) {
                while (index < t.length && (next = t[index++]) == null) {
                }
            }
            return e;
        }

    }

    final class EntryIterator extends AbstractNewIterator implements Iterator<Map.Entry<K, V>> {

        public final Map.Entry<K, V> next() {
            return nextNode();
        }

    }

    final class KeyIterator extends AbstractNewIterator implements Iterator<K> {

        public final K next() {
            return nextNode().getKey();
        }

    }

    final class ValueIterator extends AbstractNewIterator implements Iterator<V> {

        public final V next() {
            return nextNode().getValue();
        }

    }
}
