package queue;

import java.util.function.Function;

public class LinkedQueue extends AbstractQueue {
    private Node head;
    private Node tail;

    @Override
    protected Queue getInstance()
    {
        return new LinkedQueue();
    }


    @Override
    public void enqueue(Object element) {
        Node newTail = new Node(element, null);
        if (tail != null) {
            tail.prev = newTail;
        }
        tail = newTail;
        if (head == null) {
            head = tail;
        }

        IncreaseSize();
    }


    @Override
    public Object element() {
        return head.value;
    }


    @Override
    public Object dequeue() {
        Object result = head.value;
        head = head.prev;

        if(DecreaseSize() == 0)
            tail = null;

        return result;
    }

    @Override
    public void clear() {
        head = null;
        tail = null;
        ResetSize();
    }


    private static class Node {
        private Object value;
        private Node prev;

        public Node(Object value, Node prev) {
            assert value != null;

            this.value = value;
            this.prev = prev;
        }
    }
}
