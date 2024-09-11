package info.kgeorgiy.ja.tarasevich.arrayset;

import java.util.*;

public class ArraySet<E> extends AbstractSet<E> implements SortedSet<E> {
    private final List<E> data;
    protected final Comparator<? super E> comparator;

    public ArraySet() {
        this(Collections.emptyList(), null);
    }

    public ArraySet(Collection<E> collection) {
        this(collection, null);
    }

    public ArraySet(Comparator<? super E> comparator) {
        this(Collections.emptyList(), comparator);
    }

    public ArraySet(Collection<? extends E> collection, Comparator<? super E> comparator) {
        this.comparator = comparator;
        final TreeSet<E> set = new TreeSet<>(comparator);
        set.addAll(collection);
        data = List.copyOf(set);
    }

    @Override
    public Comparator<? super E> comparator() {
        return comparator;
    }

    @Override
    public SortedSet<E> subSet(E fromElement, E toElement) throws IllegalArgumentException {
        if (comparator != null && comparator.compare(fromElement, toElement) > 0) {
            // :TO-IMPROVE: messages
            throw new IllegalArgumentException("illegal argument in subset");
        }
        if (comparator == null && compare(fromElement, toElement) > 0) {
            throw new IllegalArgumentException();
        }

        int fromIndex = findIndex(fromElement);
        int toIndex = findIndex(toElement);

        if (fromIndex > toIndex) {
            throw new IllegalArgumentException();
        }

        return new ArraySet<>(data.subList(fromIndex, toIndex), comparator);
    }

    @SuppressWarnings("unchecked")
    private int compare(E firstElement, E secondElement) {
        if (comparator == null) {
            try {
                Comparator<E> comparator2 = (Comparator<E>) Comparator.naturalOrder();
                return comparator2.compare(firstElement, secondElement);
            } catch (ClassCastException e) {
                throw new ClassCastException("Cannot compare ");
            }
        }
        return comparator.compare(firstElement, secondElement);
    }

    @Override
    public SortedSet<E> headSet(E toElement) {
        int toIndex = findIndex(toElement);
        return new ArraySet<>(data.subList(0, toIndex), comparator);
    }

    @Override
    public SortedSet<E> tailSet(E fromElement) {
        int fromIndex = findIndex(fromElement);
        return new ArraySet<>(data.subList(fromIndex, size()), comparator);
    }

    @Override
    public E first() throws NoSuchElementException {
        if (this.isEmpty()) {
            throw new NoSuchElementException("the set is empty");
        }
        return data.getFirst();
    }

    @Override
    public E last() throws NoSuchElementException {
        if (this.isEmpty()) {
            throw new NoSuchElementException("the set is empty");
        }
        return data.getLast();
    }

    @Override
    public int size() {
        return data.size();
    }

    @Override
    public boolean isEmpty() {
        return data.isEmpty();
    }

    @Override
    public Iterator<E> iterator() {
        return data.iterator();
    }

    @Override
    public boolean contains(Object o) {
        return search(o) >= 0;
    }

    @SuppressWarnings("unchecked")
    public int search(Object e) {
        return Collections.binarySearch(data, (E) e, comparator);
    }

    private int findIndex(E e) {
        int elementIndex = search(e);
        if (elementIndex < 0) {
            elementIndex = elementIndex * -1 - 1;
        }
        return elementIndex;
    }

    private static class ReadOnlyItr<E> implements Iterator<E> {
        private final Iterator<E> itr;

        ReadOnlyItr(Iterator<E> itr) {
            this.itr = itr;
        }

        public boolean hasNext() {
            return itr.hasNext();
        }

        public E next() {
            return itr.next();
        }
    }
}
