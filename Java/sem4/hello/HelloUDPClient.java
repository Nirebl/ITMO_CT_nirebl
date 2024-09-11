package info.kgeorgiy.ja.tarasevich.hello;

import info.kgeorgiy.java.advanced.hello.HelloClient;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;

/**
 * {@inheritDoc}
 */
public class HelloUDPClient implements HelloClient {

    public static void main(String[] args) {
        if(args.length != 5) {
            System.err.println("Usage: HelloUDPClient <host> <prefix> <port> <number of threads> <number of requests> .");
        }

        try {
            String host = args[0];
            int port = getIntValue(args, 1);
            String prefix = args[2];
            int threads = getIntValue(args, 3);
            int requests = getIntValue(args, 4);

            new HelloUDPClient().run(host, port, prefix, threads, requests);
        } catch (Exception e) {
            System.err.println("Usage: HelloUDPClient <host> <prefix> <port> <threads> <requests> .");
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

    /**
     * {@inheritDoc}
     */
    @Override
    public void run(String host, int port, String prefix, int threads, int requests) {
        Phaser counter = new Phaser();
        counter.bulkRegister(threads + 1);

        ExecutorService executorService = Executors.newFixedThreadPool(threads);
        for (int i = 1; i <= threads; i++) {
            int callsId = i;
            executorService.execute(() -> {
                HelloCalling calling = new HelloUDPCalling(host, port);

                for (int j = 1; j <= requests; j++) {
                    while (true) {
                        String send = String.format("%s%d_%d", prefix, callsId, j);
                        String receive;
                        try {
                            receive = calling.call(send);
                        } catch (IOException e) {
                            continue;
                        }

                        if (!receive.contains(send))
                            continue;

                        System.out.println(send);
                        System.out.println(receive);

                        break;
                    }
                }
                counter.arriveAndDeregister();
            });
        }

        counter.arriveAndAwaitAdvance();
        executorService.close();
    }
}
