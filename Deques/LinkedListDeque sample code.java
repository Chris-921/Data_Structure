package deque;

import java.util.Iterator;

public class LinkedListDeque<T> implements Iterable<T>, Deque<T> {
    //Nodes in LinkedListDeque
    private class Nodes {
        private T node;
        private Nodes prev;
        private Nodes next;

        public Nodes(T value, Nodes p, Nodes n) {
            node = value;
            prev = p;
            next = n;
        }
    }

    //Front sentinel node
    private Nodes firstSen = new Nodes(null, null, null);
    //Rear sentinel node
    private Nodes lastSen = new Nodes(null, null, null);

    private int size;

    public LinkedListDeque() {
        firstSen.next = lastSen;
        lastSen.prev = firstSen;
        size = 0;

    }

    @Override
    public void addFirst(T item) {
        firstSen.next.prev = new Nodes(item, firstSen, firstSen.next);
        firstSen.next = firstSen.next.prev;
        size += 1;
    }

    @Override
    public void addLast(T item) {
        lastSen.prev.next = new Nodes(item, lastSen.prev, lastSen);
        lastSen.prev = lastSen.prev.next;
        size += 1;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void printDeque() {
        Nodes p = firstSen;
        while (p.next != lastSen) {
            if (p.next.next == lastSen) {
                System.out.println(p.next.node);
                p = p.next;
            } else {
                System.out.print(p.next.node + " ");
                p = p.next;
            }
        }
    }

    @Override
    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        T result = firstSen.next.node;
        firstSen.next.next.prev = firstSen;
        firstSen.next = firstSen.next.next;
        size -= 1;
        return result;
    }

    @Override
    public T removeLast() {
        if (size == 0) {
            return null;
        }
        T result = lastSen.prev.node;
        lastSen.prev.prev.next = lastSen;
        lastSen.prev = lastSen.prev.prev;
        size -= 1;
        return result;
    }

    @Override
    public T get(int index) {
        if (index >= size || index < 0) {
            return null;
        } else {
            Nodes p = firstSen;
            int i = 0;
            while (i != index) {
                p = p.next;
                i += 1;
            }
            return p.next.node;
        }
    }

    public boolean equals(Object o) {
        if (o instanceof Deque) {
            Deque<T> objO = (Deque<T>) o;
            if (objO.size() == this.size()) {
                for (int i = 0; i < this.size; i++) {
                    if (objO.get(i).equals(this.get(i))) {
                        continue;
                    } else {
                        return false;
                    }
                }
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public T getRecursive(int index) {
        if (index >= size || index < 0) {
            return null;
        } else {
            Nodes p = firstSen;
            return getRecursiveHelper(p, index);
        }
    }

    // Helper function for getRecursive which return value of item/node
    private T getRecursiveHelper(Nodes n, int index) {
        if (index == 0) {
            return n.next.node;
        }
        return getRecursiveHelper(n.next, index - 1);

    }

    

    public Iterator<T> iterator() {
        return new LinkedListDequeIter();

    }

    private class LinkedListDequeIter implements Iterator<T> {
        private Nodes pos;

        public LinkedListDequeIter() {
            pos = firstSen;
        }

        @Override
        public boolean hasNext() {
            return pos.next.node != null;
        }

        @Override
        public T next() {
            pos = pos.next;
            return pos.node;
        }
    }

}

