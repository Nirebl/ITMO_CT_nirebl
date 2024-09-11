package info.kgeorgiy.ja.tarasevich.bank;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Bank extends Remote {

    Person createPerson(String passport, String firstName, String lastName) throws RemoteException;

    Person getPerson(String passport, Boolean remote) throws RemoteException;

    /**
     * Returns account by identifier.
     * @param id account id
     * @return account with specified identifier or {@code null} if such account does not exist.
     */
    Account getAccount(String id) throws RemoteException;
}
