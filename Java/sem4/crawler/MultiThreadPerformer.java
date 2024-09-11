package info.kgeorgiy.ja.tarasevich.crawler;

import info.kgeorgiy.java.advanced.crawler.Document;
import info.kgeorgiy.java.advanced.crawler.Downloader;
import info.kgeorgiy.java.advanced.crawler.URLUtils;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.*;
import java.util.function.Consumer;

/**
 * Realization of multithreaded Performer that processes url
 */
class MultiThreadPerformer implements Performer {
    private final Downloader downloader;
    private final ExecutorService downloaders;
    private final int perHost;
    private final ExecutorService extractors;
    private final ConcurrentMap<String, Semaphore> usedHosts = new ConcurrentHashMap<>();


    /**
     * Constructor
     *
     * @param downloader  downloader to download the document
     * @param downloaders number of download threads
     * @param perHost     number of simultaneous downloads from the host
     * @param extractors  number of extract threads
     */
    MultiThreadPerformer(Downloader downloader, int downloaders, int perHost, int extractors) {
        this.downloader = downloader;
        this.downloaders = Executors.newFixedThreadPool(downloaders);
        this.perHost = perHost;
        this.extractors = Executors.newFixedThreadPool(extractors);
    }

    /**
     * @throws InterruptedException if there is an error while closing
     */
    public void close() throws InterruptedException {
        downloaders.shutdown();
        if (!downloaders.awaitTermination(100, TimeUnit.MILLISECONDS)) {
            downloaders.shutdownNow();
        }

        extractors.shutdown();
        if (!extractors.awaitTermination(100, TimeUnit.MILLISECONDS)) {
            extractors.shutdownNow();
        }
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void download(String url, Consumer<Document> onDownload, Consumer<IOException> onError) {
        downloaders.execute(() -> {
            try {
                var host = URLUtils.getHost(url);
                var semaphore = usedHosts.computeIfAbsent(host, ignored -> new Semaphore(perHost, true));
                Document document;
                try {
                    semaphore.acquire();
                    document = downloader.download(url);
                } finally {
                    semaphore.release();
                }
                onDownload.accept(document);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                onError.accept(e);
            }
        });
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void extract(Document doc, Consumer<List<String>> onExtract, Consumer<IOException> onError) {
        extractors.execute(() -> {
            try {
                onExtract.accept(doc.extractLinks());
            } catch (IOException e) {
                onError.accept(e);
            }
        });
    }
}
