package info.kgeorgiy.ja.tarasevich.hello;

import java.io.IOException;

/**
 * calls server
 */
interface HelloCalling {
    /**
     *
     * @param msg message to server
     * @return received response from the server
     * @throws IOException err
     */
    String call(String msg) throws IOException;
}
