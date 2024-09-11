package info.kgeorgiy.ja.tarasevich.hello;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Phaser;

class HelloUDPAnswer implements HelloAnswer {
    private static final int MAX_TIMEOUT = 100;
    private final DatagramSocket socket;
    //    private final int port;
    private final String answerPrefix;
    private final ExecutorService workers;
    private final Phaser counter = new Phaser(1);

    public HelloUDPAnswer(int port, String answerPrefix, ExecutorService workers) {
        try {
            this.socket = new DatagramSocket(port);
            this.socket.setSoTimeout(MAX_TIMEOUT);
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
        this.answerPrefix = answerPrefix;
        this.workers = workers;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void run() {
        counter.register();
        while (!socket.isClosed() && !Thread.currentThread().isInterrupted()) {
            try {
                byte[] data = new byte[socket.getReceiveBufferSize()];
                DatagramPacket packet = new DatagramPacket(data, data.length);

                socket.receive(packet);
                workers.execute(() -> {
                    counter.register();
//                    try {
//                        Thread.sleep(200);
//                    } catch (InterruptedException e) {
//                       // throw new RuntimeException(e);
//                    }
                    String request = new String(packet.getData(), packet.getOffset(), packet.getLength(), StandardCharsets.UTF_8);
                    String answer = answerPrefix.replace("$", request);

                    byte[] responseData = new byte[0];
                    DatagramPacket response = new DatagramPacket(responseData, 0, packet.getSocketAddress());

                    response.setData(answer.getBytes(StandardCharsets.UTF_8));

                    try {
                        socket.send(response);
                    } catch (IOException e) {
                        System.err.println("ERROR: I/O exception while sending: " + e.getMessage());
                    }
                    counter.arriveAndDeregister();
                });
            } catch (IOException e) {
                if (!socket.isClosed()) {
                    System.err.println("ERROR: I/O exception with datagram: " + e.getMessage());
                }
            }
        }
        counter.arriveAndDeregister();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void close() {
        socket.close();
        counter.arriveAndAwaitAdvance();
    }
}
