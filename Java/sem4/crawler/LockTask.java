package info.kgeorgiy.ja.tarasevich.crawler;

import java.util.concurrent.Phaser;
import java.util.function.Consumer;

/**
 * LockTask - Task on which the BFS waits for the completion of other tasks
 */
class LockTask implements Task, Counter {
    private final Phaser counter = new Phaser(1);

    private final Consumer<Integer> onComplete;
    private final int depth;

    /**
     * Constructor
     *
     * @param onComplete callback before end of the task
     * @param depth depth of BFS
     */
    public LockTask(Consumer<Integer> onComplete, int depth) {
        this.onComplete = onComplete;
        this.depth = depth;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean execute() throws InterruptedException {
        counter.arriveAndAwaitAdvance();

        onComplete.accept(depth);

        return depth != 1;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void registerChild() {
        counter.register();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deregisterChild() {
        counter.arriveAndDeregister();
    }
}
