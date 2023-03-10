package queue;

public class ArrayQueueADT {
    private Object[] container = new Object[2];
    private int head = 0;
    private int size = 0;

    // Pre: element != null && queue != null
    // Post: queue.n' = queue.n + 1 && any i in [1..queue.n]:
    //                                  queue.a[i]' = queue.a[i] && queue.a[queue.n + 1]' = element
    public static void enqueue(ArrayQueueADT queue, Object element) {
        assert queue != null && element != null;
        expandQueue(queue);
        queue.container[(queue.head + queue.size) % queue.container.length] = element;
        queue.size++;
    }
    // Pre: queue != null && queue.n > 0
    // Post: R = queue.a[1] && immutable
    public static Object element(ArrayQueueADT queue) {
        assert queue != null && queue.size > 0;
        return queue.container[queue.head];
    }

    // Pre: queue != null &&  queue.n > 0
    // Post: queue.n' = queue.n - 1 && (i in [1..n-1] -> queue.a[i]' = queue.a[i + 1])
    // R = queue.a[1]
    public static Object dequeue(ArrayQueueADT queue) {
        assert queue != null && queue.size > 0;
        Object resElement = element(queue);
        queue.container[queue.head] = null;
        queue.head = (queue.head + 1) % queue.container.length;
        queue.size--;
        return resElement;
    }

    // Pre: queue != null && element != null
    // Post: queue.n' = queue.n + 1 && (i in [2..queue.n+1] -> queue.a[i]' = queue.a[i - 1]) && queue.a[1]' = element
    public static void push(ArrayQueueADT queue, Object element) {
        assert element != null && queue != null;
        expandQueue(queue);
        queue.container[(queue.head - 1 + queue.container.length) % queue.container.length] = element;
        queue.head = (queue.head - 1 + queue.container.length) % queue.container.length;
        queue.size++;
    }
    // Pre: queue != null && queue.n > 0
    // Post: queue.n' = queue.n - 1 && (i in [1..queue.n-1] -> queue.a[i]' = queue.a[i])
    // R = queue.a[1]
    public static Object remove(ArrayQueueADT queue) {
        assert queue.size > 0;
        Object resElement = peek(queue);
        queue.container[(queue.head + queue.size - 1) % queue.container.length] = null;
        queue.size--;
        return resElement;
    }

    // Pre: queue != null && queue.n > 0
    // Post: R = queue.a[queue.n] && immutable
    public static Object peek(ArrayQueueADT queue) {
        assert queue.size > 0;
        return queue.container[(queue.head + queue.size - 1) % queue.container.length];
    }

    // Pre: queue != null && queue.n > ind >= 0
    // Post: R = queue.a[ind + 1] && immutable
    public static Object get(ArrayQueueADT queue, int ind) {
        assert queue.size > ind;
        return queue.container[(queue.head + ind) % queue.container.length];
    }

    // Pre: queue != null && queue.n > ind >= 0 && element != null
    // Post:  queue.n' = queue.n && (i in [1..queue.n] && i != ind + 1 -> queue.a[i]' = queue.a[i])
    // && queue.a[ind + 1]' = element
    public static void set(ArrayQueueADT queue, int ind, Object element) {
        assert queue.size > ind && element != null;
        queue.container[(queue.head + ind) % queue.container.length] = element;
    }

    // Pre: queue != null
    // Post: R = queue.n && immutable
    public static int size(ArrayQueueADT queue) {
        assert queue != null;
        return queue.size;
    }

    // Pre: queue != null
    // Post: R = (queue.n == 0) && immutable
    public static boolean isEmpty(ArrayQueueADT queue) {
        assert queue != null;
        return queue.size == 0;
    }

    // Pre: queue != null
    // Post: queue.n = 0
    public static void clear(ArrayQueueADT queue) {
        assert queue != null;
        queue.container = new Object[2];
        queue.head = queue.size = 0;
    }

    // Pre: queue != null
    // Post: elements.length' = elements.length * 2 && a'=a && n'=n &&
    // ((0 <= i < n) -> elements[i] = a[i-1]) && (n <= i < elements.length' -> elements[i] = null) && head' = 0
    private static void expandQueue(ArrayQueueADT queue) {
        if (queue.size == queue.container.length) {
            Object[] newElements = new Object[queue.container.length * 2];
            System.arraycopy(queue.container, queue.head, newElements, 0, queue.container.length - queue.head);
            System.arraycopy(queue.container, 0, newElements, queue.container.length - queue.head, queue.head);
            queue.container = newElements;
            queue.head = 0;
        }
    }

    public static int count(ArrayQueueADT arrayQueueADT, Object element)
    {
        int result = 0;
        int pos = arrayQueueADT.head;
        for (int i = 0; i < size(arrayQueueADT); i++) {
            if (arrayQueueADT.container[pos].equals(element)) {
                result++;
            }
            pos++;
            if (pos == arrayQueueADT.container.length) {
                pos = 0;
            }
        }
        return result;
    }
}
