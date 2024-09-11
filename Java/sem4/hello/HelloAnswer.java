package info.kgeorgiy.ja.tarasevich.hello;

/**
 * server
 */
interface HelloAnswer extends AutoCloseable {
    /**
     * starts server and allocates all resources.
     */
    void run();

    /**
     * Stops server and deallocates all resources.
     */
    void close();
}
