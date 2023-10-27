package deque;

import java.util.Iterator;

public class ArrayDeque<T> implements Iterable<T>, Deque<T> {
    private T[] array;
    private int size;
    private int first;
    private int last;
    private int prevSize;
    private final int startSize = 8;

    private void resizeLarger() {
        prevSize = array.length;
        T[] arrayLarger = (T[]) new Object[prevSize * 2];
        if (last == 0 || last == prevSize) {
            System.arraycopy(array, 0, arrayLarger, 0, size + 1);
        } else {
            System.arraycopy(array, first + 1, arrayLarger, 0, array.length - (first + 1));
            System.arraycopy(array, 0, arrayLarger, array.length - (first + 1), last);
        }
        array = arrayLarger;
        last = prevSize;
        first = array.length - 1;
    }

    private void resizeSmaller() {
        if (array.length < startSize * 2) {
            return;
        }
        prevSize = array.length;
        T[] arraySmaller = (T[]) new Object[prevSize / 2];
        if (first < last) {
            System.arraycopy(array, first + 1, arraySmaller, 0, size);
        } else {
            System.arraycopy(array, first + 1, arraySmaller, 0, array.length - first - 1);
            System.arraycopy(array, 0, arraySmaller, array.length - first - 1, last);
        }
        array = arraySmaller;
        last = size;
        first = array.length - 1;
    }

    public ArrayDeque() {
        array = (T[]) new Object[startSize];
        size = 0;
        prevSize = 0;
        last = startSize / 2;
        first = last - 1;

    }

    @Override
    public void addFirst(T item) {
        array[first] = item;
        first = (first - 1 + array.length) % array.length;
        if (array[first] != null) {
            resizeLarger();
        }
        size += 1;
    }

    @Override
    public void addLast(T item) {
        array[last] = item;
        last = (last + 1) % array.length;
        if (array[last] != null) {
            resizeLarger();
        }
        size += 1;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void printDeque() {
        for (int i = 0; i < array.length - 1; i++) {
            System.out.print(array[i] + " ");
        }
    }

    @Override
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        first = (first + 1) % array.length;
        T item = array[first];
        array[first] = null;
        size -= 1;
        double rate = (double) size / array.length;
        if (rate < 0.25) {
            resizeSmaller();
        }
        return item;
    }

    @Override
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        last = (last - 1 + array.length) % array.length;
        T item = array[last];
        array[last] = null;
        size -= 1;
        double rate = (double) size / array.length;
        if (rate < 0.25) {
            resizeSmaller();
        }
        return item;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= array.length || array[(first + index + 1) % array.length] == null) {
            return null;
        } else {
            return array[(first + index + 1) % array.length];
        }
    }

    public boolean equals(Object o) {
        if (o instanceof Deque) {
            Deque<T> objO = (Deque<T>) o;
            if (this.size == objO.size()) {
                for (int i = 0; i < size; i++) {
                    if (!(objO.get(i).equals(get(i)))) {
                        return false;
                    } else if ((objO.get(i) == null && get(i) != null) || (objO.get(i) != null && get(i) == null)) {
                        return false;
                    } else if (objO.get(i) == null && get(i) == null) {
                        continue;
                    }
                }
                return true;
            }
            return false;
        }
        return false;
    }

    public Iterator<T> iterator() {
        return new ArrayDequeIter();
    }

    private class ArrayDequeIter implements Iterator<T> {
        private int pos;

        public ArrayDequeIter() {
            pos = 0;
        }

        @Override
        public boolean hasNext() {
            return pos < size;
        }

        @Override
        public T next() {
            T returnItem = get(pos);
            pos += 1;
            return returnItem;
        }
    }

}
