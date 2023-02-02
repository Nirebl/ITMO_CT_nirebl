package queue;

public class ArrayQueue {

    private Object[] container = new Object[2];
    private int head = 0;
    private int size = 0;

    public void enqueue(Object element) {
        assert element != null;
        expandContainer();
        container[(head + size) % container.length] = element;
        size++;
    }

    public Object element() {
        assert size > 0;
        return container[head];
    }

    public Object dequeue() {
        assert size > 0;
        Object resElement = element();
        container[head] = null;
        head = (head + 1) % container.length;
        size--;
        return resElement;
    }

    // Post: R = n && immutable
    public int size() {
        return size;
    }

    // Post: R = (n == 0) && immutable
    public boolean isEmpty() {
        return size() == 0;
    }

    // Post: n = 0
    public void clear() {
        container = new Object[2];
        head = size = 0;
    }

    // Pre: elements != null && size != null
    // Post: elements.length > size && immutable
    private void expandContainer() {
        if (size == container.length) {
            Object[] newContailer = new Object[container.length * 2];
            System.arraycopy(container, head, newContailer, 0, container.length - head);
            System.arraycopy(container, 0, newContailer, container.length - head, head);
            container = newContailer;
            head = 0;
        }
    }

    public void push(Object element) {
        assert element != null;
        expandContainer();
        container[(head - 1 + container.length) % container.length] = element;
        head = (head - 1 + container.length) % container.length;
        size++;
    }

    // Pre: n > 0
    // Post: R = a[n] && immutable
    public Object peek() {
        assert size > 0;
        return container[(head + size - 1) % container.length];
    }

    public Object remove() {
        assert size > 0;
        Object resElement = peek();
        container[(head + size - 1) % container.length] = null;
        size--;
        return resElement;
    }


    public int count(Object element)
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
