package info.kgeorgiy.ja.tarasevich.bank;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RemotePerson extends PersonBase implements Person {
    private final int port;

    public RemotePerson(final String firstName, final String lastName, final String passport, final int port) {
        super(firstName, lastName, passport);

        this.port = port;
    }

    @Override
    protected Account createAccount(String accountId) {
        var remoteAccount = new RemoteAccount(accountId);
        try {
            UnicastRemoteObject.exportObject(remoteAccount, port);
        } catch (RemoteException e) {
            // :NOTE: не надо так делать. Пусть лучше метод честно кидает RemoteException
            throw new RuntimeException(e);
        }

        return remoteAccount;
    }

}
