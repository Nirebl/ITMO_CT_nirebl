package info.kgeorgiy.ja.tarasevich.crawler;

import info.kgeorgiy.java.advanced.crawler.*;

import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 * The WebCrawler class is implementation of AdvancedCrawler interface
 *
 * @author Nikita Tarasevich
 * {@inheritDoc}
 */
public class WebCrawler implements AdvancedCrawler {

    public static void main(String[] args) {
        if (args.length == 0) {
            System.err.println("Usage: WebCrawler url [depth [downloads [extractors [perHost]]]]");
            return;
        }

        String url = args[0];
        int depth = getValue(args, 1, 1);
        int downloads = getValue(args, 2, 1);
        int extractors = getValue(args, 3, 1);
        int perHost = getValue(args, 4, 2);

        try (var crawler = new WebCrawler(new CachingDownloader(1.0), downloads, extractors, perHost)) {
            var result = crawler.download(url, depth);
            System.out.println("Successfully downloaded:");
            result.getDownloaded().forEach(System.out::println);
            System.out.println("downloaded with errors:");
            result.getErrors().forEach((page, err) -> System.out.println(page + " :" + err.toString()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private static int getValue(String[] args, int index, int defaultValue) {
        if (index < args.length) {
            try {
                return Integer.parseInt(args[index]);
            } catch (NumberFormatException e) {
                System.err.println("Argument [" + index + "] is not a number");
                System.err.println("Usage: WebCrawler url [depth [downloads [extractors [perHost]]]]");
                return defaultValue;
            }
        } else {
            return defaultValue;
        }
    }

    private final Performer performer;

    /**
     * @param downloader  the page downloader
     * @param downloaders number of download threads
     * @param extractors number of extract threads
     * @param perHost number of simultaneous downloads from the host
     */
    public WebCrawler(Downloader downloader, int downloaders, int extractors, int perHost) {
        this.performer = new MultiThreadPerformer(downloader, downloaders, perHost, extractors);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Result download(String url, int depth) {
        return download(url, depth, new NoRuleUrlValidator());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Result download(String url, int depth, Set<String> excludes) {
        return download(url, depth, new ExcludeUrlValidator(excludes));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Result advancedDownload(String url, int depth, List<String> hosts) {
        return download(url, depth, new FollowHostsUrlValidator(hosts));
    }


    private Result download(String url, int depth, UrlValidator validator) {
        var request = new Request(performer, depth, validator);

        try {
            return request.execute(url);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void close() {
        try {
            performer.close();
        } catch (InterruptedException e) {
            System.err.println("Downloader await is interrupted");
        }
    }

}


