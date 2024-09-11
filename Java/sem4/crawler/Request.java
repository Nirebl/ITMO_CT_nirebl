package info.kgeorgiy.ja.tarasevich.crawler;

import info.kgeorgiy.java.advanced.crawler.Result;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Class that downloads urls using BFS algorithm
 */
class Request {
    private final LinkedBlockingQueue<Task> queuedTasks = new LinkedBlockingQueue<>(40960);
    private final Performer performer;
    private volatile LockTask nextLockTask;

    private final Set<String> downloaded = ConcurrentHashMap.newKeySet();
    private final ConcurrentMap<String, IOException> errors = new ConcurrentHashMap<>();

    private final Set<String> cache = new HashSet<>();
    private final UrlValidator urlValidator;

    private int depth;

    /**
     * Constructor
     *
     * @param performer    performer to process task
     * @param depth        download depth.
     * @param urlValidator validates url
     */
    public Request(Performer performer, int depth, UrlValidator urlValidator) {
        this.performer = performer;
        this.depth = depth;
        this.urlValidator = urlValidator;
    }


    private LockTask createLockTask(int depth) {
        return new LockTask((currentDepth) -> {
            try {
                this.depth = currentDepth;
                if (currentDepth < 2) {
                    return;
                }
                queuedTasks.put(nextLockTask);
                nextLockTask = createLockTask(currentDepth - 2);
            } catch (InterruptedException ignored) {
            }
        }, depth);
    }

    private Task createPerformTask(String url) {
        return new PerformTask(url, performer, nextLockTask,
                downloaded::add,
                (list) -> {
                    if (this.depth < 2) {
                        return;
                    }
                    for (String nurl : list) {
                        if (urlValidator.isUrlNotAcceptable(nurl)) {
                            continue;
                        }

                        synchronized (cache) {
                            if (cache.contains(nurl))
                                continue;
                            cache.add(nurl);
                        }

                        try {
                            queuedTasks.put(createPerformTask(nurl));
                        } catch (InterruptedException ignored) {
                        }
                    }
                },
                errors::put
        );
    }

    /**
     * @param url current url processing
     * @return download result
     * @throws InterruptedException error when interrupted
     */
    Result execute(String url) throws InterruptedException {
        if (urlValidator.isUrlNotAcceptable(url))
            return new Result(List.of(), Map.of());

        cache.add(url);
        nextLockTask = createLockTask(depth);
        queuedTasks.put(createPerformTask(url));
        queuedTasks.put(nextLockTask);

        nextLockTask = createLockTask(depth - 1);

        while (true) {
            var task = queuedTasks.take();
            if (!task.execute())
                break;
        }

        return new Result(downloaded.stream().toList(), errors);
    }

}
