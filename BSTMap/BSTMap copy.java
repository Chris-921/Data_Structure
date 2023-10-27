package bstmap;

import bstmap.Map61B;

import java.util.Iterator;
import java.util.Set;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {

    private Node root;

    private class Node {
        private K key;
        private int size;
        private V val;
        private Node left, right;

        public Node(K key, V val, int size) {
            this.key = key;
            this.val = val;
            this.size = size;
        }
    }

    @Override
    public void clear() {
        root.size = 0;
        root = null;
    }

    @Override
    public boolean containsKey(K key) {
        return containsKey(root, key);
    }
    private boolean containsKey(Node n, K key) {
        if (key == null) {
            return false;
        }
        if (n == null) {
            return false;
        }
        if (key.compareTo(n.key) < 0) {
            return containsKey(n.left, key);
        }else if (key.compareTo(n.key) > 0) {
            return containsKey(n.right, key);
        } else {
            return true;
        }

    }

    @Override
    public V get(K key) {
        return get(root, key);
    }

    private V get(Node n, K key) {
        if (key == null) {
            return null;
        }
        if (n == null) {
            return null;
        }
        if (key.compareTo(n.key) < 0) {
            return get(n.left, key);
        }else if (key.compareTo(n.key) > 0) {
            return get(n.right, key);
        } else {
            return n.val;
        }
    }

    @Override
    public int size() {
        if (root == null){
            return 0;
        }
        return root.size;
    }

    public void printInOrder() {
        if (size() == 0) {
            return;
        }

    }

    @Override
    public void put(K key, V value) {
        if (key == null) throw new IllegalArgumentException("calls put() with a null key");
        root = put(root, key, value);
    }

    private Node put(Node n, K key, V val) {
        if (n == null) {
            return new Node(key, val, 1);
        }
        if (key.compareTo(n.key) < 0) {
            n.left = put(n.left, key, val);
        } else if (key.compareTo(n.key) > 0) {
            n.right = put(n.right, key, val);
        } else {
            n.val = val;
        }
        n.size = 1 + size(n.left) + size(n.right);
        return n;
    }
    private int size(Node n) {
        if (n == null) return 0;
        else return n.size;
    }

    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException("Unsupported Operation.");
    }

    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException("Unsupported Operation.");
    }

    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException("Unsupported Operation.");
    }

    @Override
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException("Unsupported Operation.");
    }

}
