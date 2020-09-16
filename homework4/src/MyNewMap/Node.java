package MyNewMap;

import java.util.Map;
import java.util.Objects;

/**
 * Package private class for Objects with key, and value.
 */
class Node <K, V> implements Map.Entry<K,V> {
    private K key;
    private V value;
    Node<K, V> next;

    public Node<K, V> getNext() {
        return next;
    }

    public void setNext(Node<K, V> next) {
        this.next = next;
    }

    public void setCurrent(Node<K, V> current){
        if (current != null){
            this.key = current.key;
            this.value = current.value;
            this.next = current.getNext();
        }
    }


    public Node(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    @Override
    public V getValue() {
        return value;
    }


    @Override
    public V setValue(V newValue) {
        V oldValue = newValue;
        this.value = newValue;
        return oldValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node<?, ?> node = (Node<?, ?>) o;
        return Objects.equals(key, node.key) &&
                Objects.equals(value, node.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key) * 32;
    }
}
