package queue;

public class ArrayQueue extends AbstractQueue {

    private Object[] container = new Object[2];
    private int head = 0;

    //Let eq(n) is:
    //(i in [1..n] -> a[i]' = a[i])

    //Let immutable(q):
    //n'=n && eq(n)

    @Override
    protected Queue getInstance()
    {
        return new ArrayQueue();
    }

    // Pre: element != null
    // Post: n' = n + 1 && eq(n) && a[n + 1]' = element
    @Override
    public void enqueue(Object element) {
        expandContainer();
        container[(head + size()) % container.length] = element;
        IncreaseSize();
    }
    // Pre: n > 0
    // Post: R = a[1] && immutable(container)
    @Override
    public Object element() {
        return container[head];
    }
    // Pre: n > 0
    // Post: n' = n - 1 && eq(n - 1)
    // R = a[1]
    @Override
    public Object dequeue() {
        Object resElement = element();
        container[head] = null;
        head = (head + 1) % container.length;
        DecreaseSize();
        return resElement;
    }

    // Pred: true
    // Post: n = 0
    public void clear() {
        container = new Object[2];
        head = 0;
        ResetSize();
    }

    // Pre: true
    // Post: elements.length' = elements.length * 2 && a'=a && n'=n &&
    // ((0 <= i < n) -> elements[i] = a[i-1]) && (n <= i < elements.length' -> elements[i] = null) && head' = 0
    private void expandContainer() {
        if (size() == container.length) {
            Object[] newContailer = new Object[container.length * 2];
            System.arraycopy(container, head, newContailer, 0, container.length - head);
            System.arraycopy(container, 0, newContailer, container.length - head, head);
            container = newContailer;
            head = 0;
        }
    }

    // Pre: element != null
    // Post: n' = n + 1 && (i in [2..n+1] -> a[i]' = a[i - 1]) && a[1]' = element
    public void push(Object element) {
        assert element != null;
        expandContainer();
        container[(head - 1 + container.length) % container.length] = element;
        head = (head - 1 + container.length) % container.length;
        IncreaseSize();
    }

    // Pre: n > 0
    // Post: R = a[n] && immutable(container)
    public Object peek() {
        return container[(head + size() - 1) % container.length];
    }

    // Pre: n > 0
    // Post: n' = n - 1 && (i in [1..n-1] -> a[i]' = a[i])
    // R = a[1]
    public Object remove() {
        Object result = peek();
        container[(head + size() - 1) % container.length] = null;
        DecreaseSize();

        return result;
    }

    //Pre: element != null
    //Post: result = |{i | container[i] == element}|
    public int count(Object element) {
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
