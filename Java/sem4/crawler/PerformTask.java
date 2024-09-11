package info.kgeorgiy.ja.tarasevich.crawler;

import java.io.IOException;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * Task that performs downloading and extracting in BFS queue
 */
class PerformTask implements Task {
    private final String url;
    private final Performer performer;
    private final Counter counter;


    private final Consumer<String> onSuccess;
    private final Consumer<List<String>> onExtract;
    private final BiConsumer<String, IOException> onFailure;

    /**
     * Constructor
     *
     * @param url       url of the task
     * @param performer performer that will process downloading and extracting
     * @param counter   counter to count a quantity of child tasks
     * @param onSuccess callback for success downloaded url
     * @param onExtract callback for success extracted url
     * @param onFailure callback for failed downloaded or extracted url
     */
    public PerformTask(String url, Performer performer, Counter counter,
                       Consumer<String> onSuccess, Consumer<List<String>> onExtract,
                       BiConsumer<String, IOException> onFailure) {

        this.counter = counter;
        this.counter.registerChild();

        this.url = url;
        this.performer = performer;
        this.onSuccess = onSuccess;
        this.onExtract = onExtract;
        this.onFailure = onFailure;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean execute() throws InterruptedException {
        performer.download(url, (doc) -> {
            counter.registerChild();
            performer.extract(doc, (list) -> {
                onSuccess.accept(url);
                onExtract.accept(list);
                counter.deregisterChild();
            }, (ex) -> {
                onFailure.accept(url, ex);
                counter.deregisterChild();
            });
            this.counter.deregisterChild();
        }, (ex) -> {
            onFailure.accept(url, ex);
            counter.deregisterChild();
        });

        return true;
    }
}
