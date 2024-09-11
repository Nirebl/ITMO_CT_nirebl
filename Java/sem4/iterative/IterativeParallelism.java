package info.kgeorgiy.ja.tarasevich.iterative;

import info.kgeorgiy.java.advanced.iterative.NewScalarIP;
import info.kgeorgiy.java.advanced.mapper.ParallelMapper;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * The IterativeParallelism class is implementation of ScalarIP interface
 *
 * @author Nikita Tarasevich
 */
public class IterativeParallelism implements NewScalarIP {

    private ParallelMapper mapper;

    public IterativeParallelism() {
    }

    public IterativeParallelism(ParallelMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public <T> T maximum(int threads, List<? extends T> list, Comparator<? super T> comparator) throws InterruptedException {
        return maximum(threads, list, comparator, 1);
    }

    @Override
    public <T> T minimum(int threads, List<? extends T> list, Comparator<? super T> comparator) throws InterruptedException {
        return minimum(threads, list, comparator, 1);
    }

    @Override
    public <T> boolean all(int threads, List<? extends T> list, Predicate<? super T> predicate) throws InterruptedException {
        return all(threads, list, predicate, 1);
    }

    @Override
    public <T> boolean any(int threads, List<? extends T> list, Predicate<? super T> predicate) throws InterruptedException {
        return any(threads, list, predicate, 1);
    }

    @Override
    public <T> int count(int threads, List<? extends T> list, Predicate<? super T> predicate) throws InterruptedException {
        return count(threads, list, predicate, 1);
    }

    @Override
    public <T> T maximum(int threads, List<? extends T> list, Comparator<? super T> comparator, int step) throws InterruptedException {
        return execute(threads, list, (lst) -> lst.stream().max(comparator).orElseThrow(), step).max(comparator).orElseThrow();
    }

    @Override
    public <T> T minimum(int threads, List<? extends T> list, Comparator<? super T> comparator, int step) throws InterruptedException {
        return maximum(threads, list, comparator.reversed(), step);
    }

    @Override
    public <T> boolean all(int threads, List<? extends T> list, Predicate<? super T> predicate, int step) throws InterruptedException {
        return execute(threads, list, (lst) -> lst.stream().allMatch(predicate), step).allMatch(b -> b);
    }

    @Override
    public <T> boolean any(int threads, List<? extends T> list, Predicate<? super T> predicate, int step) throws InterruptedException {
        return execute(threads, list, (lst) -> lst.stream().anyMatch(predicate), step).anyMatch(b -> b);
    }

    @Override
    public <T> int count(int threads, List<? extends T> list, Predicate<? super T> predicate, int step) throws InterruptedException {
        var result = execute(threads, list, (lst) -> lst.stream().filter(predicate).count(), step).map(Long::longValue).reduce(0L, Long::sum);

        if (result > Integer.MAX_VALUE) {
            throw new InterruptedException("Count is bigger than Integer.MAX_VALUE");
        }

        return result.intValue();
    }

    private <T, Out> Stream<? extends Out> execute(int threads, List<? extends T> list, Function<List<? extends T>, ? extends Out> handler, int step)
            throws InterruptedException {

        var tasks = split(threads, list, step);

        if (mapper != null) {
            return mapper.map(handler, tasks).stream();
        } else {
            var pairs = tasks.stream().map(slice -> new Performer<>(slice, handler))
                    .map(performer -> new SimplePair<>(performer, new Thread(performer)))
                    .peek(pair -> pair.second.start()).toList();

            for (var pair : pairs)
                pair.second.join();

            return pairs.stream().map(p -> p.first.getResult());
        }
    }

    private <T> List<? extends List<? extends T>> split(int parts, List<? extends T> list, int step) {
        var stepList = new StepReadOnlyList<>(list, step);

        if (parts <= 1)
            return List.of(stepList);

        final var lparts = Math.min(parts, stepList.size());
        final int lsize = stepList.size() / lparts;

        return IntStream.range(0, lparts)
                .mapToObj(i -> stepList.subList(i * lsize, i < lparts - 1 ? (i + 1) * lsize : stepList.size()))
                .toList();
    }

    private static class SimplePair<F, S> {
        public final F first;
        public final S second;

        public SimplePair(F first, S second) {
            this.first = first;
            this.second = second;
        }
    }

    private static class Performer<T, Out> implements Runnable {
        private Out result;
        private final List<? extends T> source;
        private final Function<List<? extends T>, Out> handler;

        public Performer(List<? extends T> source, Function<List<? extends T>, Out> handler) {
            this.source = source;
            this.handler = handler;
        }

        Out getResult() {
            return result;
        }

        @Override
        public void run() {
            result = handler.apply(source);
        }
    }

    private static class StepReadOnlyList<E> implements List<E> {
        private final List<E> source;
        private final int step;
        private final int offset;
        private final int fromIndex;
        private final int toIndex;

        public StepReadOnlyList(List<E> source, int step) {
            this.source = source;
            this.step = step;
            this.offset = 0;
            this.fromIndex = 0;
            this.toIndex = (source.size() + step - 1) / step - 1;
        }

        private StepReadOnlyList(StepReadOnlyList<E> parent, int step, int fromIndex, int toIndex) {
            this.source = parent.source;
            this.step = step;
            this.offset = parent.offset + fromIndex * step;
            this.fromIndex = fromIndex;
            this.toIndex = toIndex;
        }

        @Override
        public int size() {
            return toIndex - fromIndex + 1;
        }

        @Override
        public boolean isEmpty() {
            return size() == 0;
        }

        @Override
        public boolean contains(Object o) {
            throw new UnsupportedOperationException("contains");
        }

        @Override
        public Iterator<E> iterator() {
            return new Itr();
        }

        @Override
        public Object[] toArray() {
            throw new UnsupportedOperationException("toArray");
        }

        @Override
        public <T> T[] toArray(T[] a) {
            throw new UnsupportedOperationException("toArray");
        }

        @Override
        public boolean add(E e) {
            throw new UnsupportedOperationException("add");
        }

        @Override
        public boolean remove(Object o) {
            throw new UnsupportedOperationException("remove");
        }

        @Override
        public boolean containsAll(Collection<?> c) {
            throw new UnsupportedOperationException("containsAll");
        }

        @Override
        public boolean addAll(Collection<? extends E> c) {
            throw new UnsupportedOperationException("addAll");
        }

        @Override
        public boolean addAll(int index, Collection<? extends E> c) {
            throw new UnsupportedOperationException("addAll");
        }

        @Override
        public boolean removeAll(Collection<?> c) {
            throw new UnsupportedOperationException("removeAll");
        }

        @Override
        public boolean retainAll(Collection<?> c) {
            throw new UnsupportedOperationException("retainAll");
        }

        @Override
        public void clear() {
            throw new UnsupportedOperationException("clear");
        }

        @Override
        public E get(int index) {
            Objects.checkIndex(index, size());
            return source.get(offset + index * step);
        }

        @Override
        public E set(int index, E element) {
            throw new UnsupportedOperationException("set");
        }

        @Override
        public void add(int index, E element) {
            throw new UnsupportedOperationException("add");
        }

        @Override
        public E remove(int index) {
            throw new UnsupportedOperationException("remove");
        }

        @Override
        public int indexOf(Object o) {
            throw new UnsupportedOperationException("indexOf");
        }

        @Override
        public int lastIndexOf(Object o) {
            throw new UnsupportedOperationException("lastIndexOf");
        }

        @Override
        public ListIterator<E> listIterator() {
            throw new UnsupportedOperationException("listIterator");
        }

        @Override
        public ListIterator<E> listIterator(int index) {
            throw new UnsupportedOperationException("listIterator");
        }

        @Override
        public List<E> subList(int fromIndex, int toIndex) {
            subListRangeCheck(fromIndex, toIndex, size());
            return new StepReadOnlyList<>(this, step, fromIndex, toIndex - 1);
        }

        static void subListRangeCheck(int fromIndex, int toIndex, int size) {
            if (fromIndex < 0)
                throw new IndexOutOfBoundsException("fromIndex = " + fromIndex);
            if (toIndex > size)
                throw new IndexOutOfBoundsException("toIndex = " + toIndex);
            if (fromIndex > toIndex)
                throw new IllegalArgumentException("fromIndex(" + fromIndex +
                        ") > toIndex(" + toIndex + ")");
        }

        private class Itr implements Iterator<E> {
            int cursor;

            public Itr() {
                cursor = 0;
            }

            @Override
            public boolean hasNext() {
                return cursor < size();
            }

            @Override
            public E next() {
                int i = cursor;
                if (i >= size())
                    throw new NoSuchElementException();
                cursor = i + 1;
                return source.get(offset + i * step);
            }
        }
    }

}
