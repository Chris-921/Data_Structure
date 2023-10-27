package deque;


import java.util.Comparator;

public class MaxArrayDeque<T> extends ArrayDeque<T> {
    private Comparator<T> cp;

    public MaxArrayDeque(Comparator<T> c) {
        cp = c;
    }

    public T max() {
        if (isEmpty()) {
            return null;
        } else {
            T max = get(0);
            for (int i = 1; i < size(); i++) {
                if (cp.compare(max, get(i)) < 0) {
                    max = get(i);
                }
            }
            return max;
        }
    }

    public T max(Comparator<T> c) {
        if (isEmpty()) {
            return null;
        } else {
            T max = get(0);
            for (int i = 1; i < size(); i++) {
                if (c.compare(max, get(i)) < 0) {
                    max = get(i);
                }
            }
            return max;
        }
    }
}
