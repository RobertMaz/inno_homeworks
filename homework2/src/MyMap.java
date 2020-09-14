import java.util.Objects;


/**
 * Class implementation hashtable, how HashMap.
 * Implements Map.
 */
public class MyMap implements Map {

    /**
     * The default initial capacity - MUST be a power of two.
     */
    static final int DEFAULT_INITIAL_CAPACITY = 16; // aka 16

    /**
     * The maximum capacity, used if a higher value is implicitly specified
     * by either of the constructors with arguments.
     * MUST be a power of two <= 1<<30.
     */
    static final int MAXIMUM_CAPACITY = Integer.MAX_VALUE;

    private Node[] table;

    /**
     * Table capacity
     */
    private int capacity;


    /**
     * Count all Objects in table
     */
    private int objectCount;

    /**
     * Count not null elements table
     */
    private int bucketsCount;


    /**
     * Constructor
     *
     * @param capacity throws IllegalArgumentException, when capacity < 0
     */
    public MyMap(int capacity) {
        if (capacity < 0) {
            throw new IllegalArgumentException("Illegal initial capacity: " + capacity);
        }
        this.capacity = capacity;
        table = new Node[capacity];
    }

    public MyMap() {
        this(DEFAULT_INITIAL_CAPACITY);
    }

    /**
     * Method is put new element in table. If element by hashcode in table is not null,
     * then use putNodeByEqualsHash(newNode, bucket).
     *
     * @param key
     * @param value
     */
    @Override
    public void put(Object key, Object value) {
        if (key == null) {
            throw new IllegalArgumentException("Key can't be null");
        }

        Node newNode = new Node(key, value);
        int hashCode = Math.abs(newNode.hashCode() % capacity);

        if (isFull()) {
            increaseCapacity();
        }

        if (table[hashCode] == null) {
            table[hashCode] = newNode;
            objectCount++;
            bucketsCount++;
        } else {
            putNodeByEqualsHash(newNode, table[hashCode]);
        }
    }

    /**
     * Method return Value by Key. If not find key, throw IllegalArgumentException.
     *
     * @param key
     * @return Value if find, else null
     */
    @Override
    public Object getValue(Object key) {
        int hash = Math.abs(new Node(key, null).hashCode() % capacity);
        Node current = table[hash];
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
     * Method delete object in table by key, and return old Value.
     * If not find object, throw new IllegalArgumentException.
     *
     * @param key
     * @return oldValue by Key if key find, else throw new IllegalArgumentException.
     */
    @Override
    public Object delete(Object key) {
        Node deleteNode = new Node(key, null);
        int currentHash = Math.abs(deleteNode.hashCode() % capacity);
        Object oldValue = null;

        if (table[currentHash] == null) {
            throw new IllegalArgumentException("Not find object with key=" + key);
        }

        Node head = table[currentHash];
        if (head.getNext() == null && head.getKey().equals(deleteNode.getKey())) {
            oldValue = table[currentHash].getValue();
            table[currentHash] = null;
            objectCount--;
        } else {
            while (head.getNext() != null) {
                if (head.getNext().getKey().equals(deleteNode.getKey())) {
                    oldValue = head.getNext().getValue();
                    head.setNext(head.getNext().getNext());
                    objectCount--;
                    break;
                }
                head = head.getNext();
            }
        }

        return oldValue;
    }

    /**
     * Method update value element by key.
     * If update successful, then return oldValue.
     * Else key not find, then throws IllegalArgumentException
     *
     * @param key
     * @param value
     * @return oldValue if update success, else throw IllegalArgumentException.
     */
    @Override
    public Object update(Object key, Object value) {
        Node node = new Node(key, value);
        int hash = Math.abs(node.hashCode() % capacity);
        Object oldValue = putNodeByEqualsHash(node, table[hash]);

        if (oldValue == null) {
            throw new IllegalArgumentException("Not find element with key=" + key);
        }
        return oldValue;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (Node node : table) {
            if (node != null) {
                sb.append(node);
                sb.append(", ");
            }
        }
        sb.delete(sb.length() - 2, sb.length());
        return "MyMap" + sb.toString() + "]";
    }

    /**
     * @return count Objects in table
     */
    @Override
    public int size() {
        return objectCount;
    }

    /**
     * Method return true if key is was find. else false.
     *
     * @param key
     * @return
     */
    @Override
    public boolean containsKey(Object key) {
        return getValue(key) != null;
    }

    /**
     * Method put new Element in table, if hashcode is equals.
     * If table have key yet, then value by key is rewrite.     *
     *
     * @param newNode      is new element for table
     * @param bucketForPut bucket fot linkedList
     * @return oldValue if Value was rewrite, else null
     */

    private Object putNodeByEqualsHash(Node newNode, Node bucketForPut) {
        Node head = bucketForPut;
        Object oldValue = null;

        while (head != null) {
            if (head.getKey().equals(newNode.getKey())) {
                oldValue = head.getValue();
                head.setValue(newNode.getValue());
                break;
            } else if (head.getNext() == null) {
                head.setNext(newNode);
                objectCount++;
                break;
            }
            head = head.getNext();
        }
        return oldValue;
    }

    /**
     * Method for check capacity.
     * If bucketCount more then capacity by 80 %,
     * return true, else return false.
     *
     * @return
     */
    private boolean isFull() {
        return bucketsCount > capacity * 0.8;
    }

    /**
     * Method increase capacity in table.
     */
    private void increaseCapacity() {
        bucketsCount = 0;
        objectCount = 0;
        capacity *= 2;
        Node[] oldTable = table;
        if (capacity >= MAXIMUM_CAPACITY) {
            throw new ArrayIndexOutOfBoundsException("Array is full");
        }
        table = new Node[capacity];

        for (Node node : oldTable) {
            if (node != null) {
                put(node.getKey(), node.getValue());
            }
        }
    }

}
