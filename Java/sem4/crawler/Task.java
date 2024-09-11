package info.kgeorgiy.ja.tarasevich.crawler;

/**
 * Component in queue in BFS algorithm
 */
interface Task {
    /**
     * @return true if task completed successfully and BFS should be continued
     * @throws InterruptedException error while interrupted
     */
    boolean execute() throws InterruptedException;
}
