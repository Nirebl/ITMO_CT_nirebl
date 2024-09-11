package info.kgeorgiy.ja.tarasevich.crawler;

/**
 * Counter control for child task
 */
interface Counter {
    /**
     * method to register child task
     */
    void registerChild();

    /**
     * method to deregister child task
     */
    void deregisterChild();
}
