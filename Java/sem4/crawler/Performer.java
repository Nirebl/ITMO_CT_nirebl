package info.kgeorgiy.ja.tarasevich.crawler;

import info.kgeorgiy.java.advanced.crawler.Document;

import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;

/**
 * Interface for performer that processes url
 */
interface Performer {
    /**
     * @param url        current url to download
     * @param onDownload callback for success downloaded url
     * @param onError    callback for failed downloaded url
     */
    void download(String url, Consumer<Document> onDownload, Consumer<IOException> onError);

    /**
     * @param doc       current document
     * @param onExtract callback for success extracted url
     * @param onError   callback for failed extracted url
     */
    void extract(Document doc, Consumer<List<String>> onExtract, Consumer<IOException> onError);

    /**
     * end performer work
     * @throws InterruptedException when closing thrown an error
     */
    void close() throws InterruptedException;
}
