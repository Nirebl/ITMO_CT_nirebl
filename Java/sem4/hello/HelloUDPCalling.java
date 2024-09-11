package info.kgeorgiy.ja.tarasevich.hello;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * {@inheritDoc}
 */
class HelloUDPCalling implements HelloCalling {
    private final static int BUFFER_SIZE = 1536;
    private final static int MAX_TIMEOUT = 250;

    private final String host;
    private final int port;

    private final DatagramSocket socket;

    public HelloUDPCalling(String host, int port) {
        this.host = host;
        this.port = port;

        try {
            socket = new DatagramSocket();
            socket.setSoTimeout(MAX_TIMEOUT);
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String call(String msg) throws IOException {
        DatagramPacket sendPacket = new DatagramPacket(new byte[BUFFER_SIZE], BUFFER_SIZE, new InetSocketAddress(host, port));
        sendPacket.setData(msg.getBytes(StandardCharsets.UTF_8));

        socket.send(sendPacket);

        DatagramPacket receivePacket = new DatagramPacket(new byte[BUFFER_SIZE], BUFFER_SIZE);
        socket.receive(receivePacket);

        return new String(Arrays.copyOf(receivePacket.getData(), receivePacket.getLength()), StandardCharsets.UTF_8);
    }
}
