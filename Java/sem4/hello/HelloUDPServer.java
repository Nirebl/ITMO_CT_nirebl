package info.kgeorgiy.ja.tarasevich.hello;

import info.kgeorgiy.java.advanced.hello.NewHelloServer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * {@inheritDoc}
 */
public class HelloUDPServer implements NewHelloServer {

    /**
     * Main function for starting {@link HelloUDPServer}.
     * <p>
     * Usage: <port> <number of threads.
     *
     * @param args array of arguments: {@link Integer} port, {@link Integer} threads.
     */
    public static void main(String[] args) {
        try {
            int port = getIntValue(args, 0);
            int threads = getIntValue(args, 1);

            var helloServer = new HelloUDPServer();
            var thread = new Thread(() -> helloServer.start(port, threads));

            System.out.println("Press any key to exit");
            System.in.read();

            helloServer.close();
            thread.interrupt();

        } catch (Exception e) {
            System.err.println("Usage: <port> <number of threads> .");
        }
    }

    private static int getIntValue(String[] args, int index) {
        try {
            return Integer.parseInt(args[index]);
        } catch (NumberFormatException e) {
            System.err.println("Argument [" + index + "] is not a number");
            throw e;
        }
    }

    private ExecutorService workers;
    private ExecutorService receiver;
    private final List<HelloAnswer> answers = new ArrayList<>();

    /**
     * {@inheritDoc}
     */
    @Override
    public void start(int threads, Map<Integer, String> map) {
        if (map.isEmpty())
            return;

        receiver = Executors.newFixedThreadPool(map.size());
        workers = Executors.newFixedThreadPool(threads);

        map.forEach((port, answer) -> {
            HelloAnswer server = new HelloUDPAnswer(port, answer, workers);
            answers.add(server);
            receiver.execute(server::run);
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void close() {
        answers.forEach(HelloAnswer::close);
        answers.clear();

        if (receiver != null)
            receiver.shutdownNow();

        if (workers != null)
            workers.shutdownNow();

    }

}
