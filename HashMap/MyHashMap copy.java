package hashmap;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

/**
 * A hash table-backed Map implementation. Provides amortized constant time
 * access to elements via get(), remove(), and put() in the best case.
 * <p>
 * Assumes null keys will never be inserted, and does not resize down upon remove().
 *
 * @author Zilong Guo
 */
public class MyHashMap<K, V> implements Map61B<K, V> {

    @Override
    public void clear() {
        buckets = createTable(DEFAULT_INIIT_SIZE);
        size = 0;

    }

    @Override
    public boolean containsKey(K key) {
        Node node = getNode(key);
        return node != null;
    }

    @Override
    public V get(K key) {
        Node node = getNode(key);
        if (node == null) {
            return null;
        }
        return node.value;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void put(K key, V value) {
        Node n = getNode(key);
        if (n != null) {
            n.value = value;
        } else {
            int bucketIndex = findBucket(key);
            Collection<Node> c = buckets[bucketIndex];

            if (c == null) {
                buckets[bucketIndex] = createBucket();
            }
            buckets[bucketIndex].add(new Node(key, value));
            size++;

            if ((double) size / buckets.length >= loadFactor) {
                rebucket(buckets.length * 2);
            }
        }
    }

    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException();
    }

    /**
     * Protected helper class to store key/value pairs
     * The protected qualifier allows subclass access
     */
    protected class Node {
        K key;
        V value;

        Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    /* Instance Variables */
    private Collection<Node>[] buckets;
    public static final int DEFAULT_INIIT_SIZE = 16;
    public static final double DEFAULT_LOAD_FACTOR = 0.75;
    private int size;
    private double loadFactor;

    /**
     * Constructors
     */
    public MyHashMap() {
        this(DEFAULT_INIIT_SIZE, DEFAULT_LOAD_FACTOR);
    }

    public MyHashMap(int initialSize) {
        this(initialSize, DEFAULT_LOAD_FACTOR);
    }

    /**
     * MyHashMap constructor that creates a backing array of initialSize.
     * The load factor (# items / # buckets) should always be <= loadFactor
     *
     * @param initialSize initial size of backing array
     * @param maxLoad     maximum load factor
     */
    public MyHashMap(int initialSize, double maxLoad) {
        buckets = createTable(initialSize);
        size = 0;
        loadFactor = maxLoad;
    }

    /**
     * Returns a new node to be placed in a hash table bucket
     */
    private Node createNode(K key, V value) {
        return new Node(key, value);
    }

    /**
     * Returns a data structure to be a hash table bucket
     * <p>
     * The only requirements of a hash table bucket are that we can:
     * 1. Insert items (`add` method)
     * 2. Remove items (`remove` method)
     * 3. Iterate through items (`iterator` method)
     * <p>
     * Each of these methods is supported by java.util.Collection,
     * Most data structures in Java inherit from Collection, so we
     * can use almost any data structure as our buckets.
     * <p>
     * Override this method to use different data structures as
     * the underlying bucket type
     */
    protected Collection<Node> createBucket() {
        return new LinkedList<>();
    }

    /**
     * Returns a table to back our hash table. As per the comment
     * above, this table can be an array of Collection objects
     *
     * @param tableSize the size of the table to create
     */
    private Collection<Node>[] createTable(int tableSize) {
        return new Collection[tableSize];
    }

    private int findBucket(K key) {
        return findBucket(key, buckets.length);
    }

    private int findBucket(K key, int numBuckets) {
        return Math.floorMod(key.hashCode(), numBuckets);
    }

    private Node getNode(K key) {
        int idx = findBucket(key);
        Collection<Node> bucketList = buckets[idx];
        if (bucketList != null) {
            for (Node node : bucketList) {
                if (node.key.equals(key)) {
                    return node;
                }
            }
        }
        return null;
    }

    private void rebucket(int targetSize) {
        Collection<Node>[] newBuckets = createTable(targetSize);
        for (Collection<Node> c : this.buckets) {
            if (c == null) {
                continue;
            }
            for (Node n : c) {
                K key = n.key;
                int bucketIndex = findBucket(key, newBuckets.length);
                if (newBuckets[bucketIndex] == null) {
                    newBuckets[bucketIndex] = createBucket();
                }
                newBuckets[bucketIndex].add(n);
            }
        }
        buckets = newBuckets;
    }
}
