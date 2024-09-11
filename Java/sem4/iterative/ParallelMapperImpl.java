package info.kgeorgiy.ja.tarasevich.iterative;

import info.kgeorgiy.java.advanced.mapper.ParallelMapper;

import java.util.*;
import java.util.function.Function;

/**
 * The IterativeParallelism class is implementation of ParallelMapper interface
 *
 * @author Nikita Tarasevich
 */
public class ParallelMapperImpl implements ParallelMapper {
    private final List<Thread> threadPool;
    private final Queue<Runnable> tasks = new ArrayDeque<>();
    private static final int MAX_TASKS = 1024;

    /**
     * Constructor
     *
     * @param numThreads { @link int}: number of threads
     */
    public ParallelMapperImpl(int numThreads) {
        var performer = new Performer();
        threadPool = Arrays.asList(new Thread[numThreads]);
        // :NOTE: optimize
        for (int i = 0; i < numThreads; i++) {
            var thread = new Thread(performer);
            threadPool.set(i, thread);
            thread.start();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T, R> List<R> map(Function<? super T, ? extends R> f, List<? extends T> args) throws InterruptedException {
        var result = new ResultsAccumulator<R>(args.size());
        var e = new RuntimeException();
        for (int i = 0; i < args.size(); i++) {
            int index = i;
            addTask(() -> {
                        try {
                            result.set(index, f.apply(args.get(index)));
                        } catch (RuntimeException ee) {
                            result.set(index, null);
                            synchronized (e) {
                                e.addSuppressed(ee);
                            }
                        }
                    }
            );
        }
        // :NOTE: to think
        var data = result.getData();
        if (e.getSuppressed().length > 0) {
            throw e;
        }

        return data;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void close() {
        threadPool.forEach(Thread::interrupt);

        threadPool.forEach(t -> {
                    try {
                        t.join();
                    } catch (InterruptedException interruptedException) {
                    }
                }
        );
    }

    private void addTask(Runnable query) throws InterruptedException {
        synchronized (tasks) {
            while (tasks.size() > MAX_TASKS) {
                tasks.wait();
            }
            tasks.add(query);
            tasks.notifyAll();
        }
    }

    private class Performer implements Runnable {
        @Override
        public void run() {
            try {
                while (!Thread.interrupted()) {
                    Runnable task;
                    synchronized (tasks) {
                        while (tasks.isEmpty()) {
                            tasks.wait();
                        }
                        task = tasks.poll();
                        tasks.notifyAll();
                    }
                    task.run();
                }
            } catch (InterruptedException interruptedException) {
            } finally {
                Thread.currentThread().interrupt();
            }
        }
    }

    static class ResultsAccumulator<R> {
        private final List<R> data;
        private int count;

        public ResultsAccumulator(int size) {
            data = new ArrayList<>(Collections.nCopies(size, null));
            count = 0;
        }

        public void set(int index, R value) {
            data.set(index, value);
            synchronized (data) {
                if (++count >= data.size()) {
                    data.notifyAll();
                }
            }
        }

        public List<R> getData() throws InterruptedException {
            synchronized (data) {
                while (count < data.size()) {
                    data.wait();
                }
            }

            return data;
        }
    }
}
