package queue;

public class ArrayQueueModule {
    private static Object[] container = new Object[2];
    private static int head = 0;
    private static int size = 0;

    // Pre: element != null
    // Post: n' = n + 1 && (i in [1..n] -> a[i]' = a[i]) && a[n + 1]' = element
    public static void enqueue(Object element) {
        assert element != null;
        expandQueue();
        container[(head + size) % container.length] = element;
        size++;
    }

    // Pre: n > 0
    // Post: R = a[1] && immutable
    public static Object element() {
        return container[head];
    }

    // Pre: n > 0
    // Post: n' = n - 1 && (i in [1..n-1] -> a[i]' = a[i + 1])
    // R = a[1]
    public static Object dequeue() {
        Object resElement = element();
        container[head] = null;
        head = (head + 1) % container.length;
        size--;

        return resElement;
    }

    // Post: R = n && immutable
    public static int size() {
        //return head > size ? (elements.length - head + size) : (size - head);
        return size;
    }

    // Post: R = (n == 0) && immutable
    public static boolean isEmpty() {
        return 0 == size;
    }

    // Post: n = 0
    public static void clear() {
        container = new Object[2];
        head = size = 0;
    }

    // Pre: true
    // Post: elements.length' = elements.length * 2 && a'=a && n'=n &&
    // ((0 <= i < n) -> elements[i] = a[i-1]) && (n <= i < elements.length' -> elements[i] = null) && head' = 0
    private static void expandQueue() {
        if (container.length > size())
            return;

        Object[] newContainer = new Object[container.length * 2];
        if (head + size > container.length) {
            System.arraycopy(container, head, newContainer, 0, container.length - head);
            System.arraycopy(container, 0, newContainer, container.length - head, head);
        } else
            System.arraycopy(container, head, newContainer, 0, size);

        container = newContainer;
        head = 0;
    }

    // Pre: element != null
    // Post: n' = n + 1 && (i in [2..n+1] -> a[i]' = a[i - 1]) && a[1]' = element
    public static void push(Object element) {
        assert element != null;
        expandQueue();
        container[(head - 1 + container.length) % container.length] = element;
        head = (head - 1 + container.length) % container.length;
        size++;
    }

    // Pre: n > 0
    // Post: R = a[n] && immutable
    public static Object peek() {
        assert size > 0;
        return container[(head + size - 1) % container.length];
    }

    // Pre: n > 0
    // Post: n' = n - 1 && (i in [1..n-1] -> a[i]' = a[i])
    // R = a[1]
    public static Object remove() {
        assert size > 0;
        Object resElement = peek();
        container[(head + size - 1) % container.length] = null;
        size--;
        return resElement;
    }

    public static int count(Object element)
    {
        int result = 0;
        int pos = head;
        for (int i = 0; i < size(); i++) {
            if (container[pos].equals(element)) {
                result++;
            }
            pos++;
            if (pos == container.length) {
                pos = 0;
            }
        }
        return result;
    }
}

