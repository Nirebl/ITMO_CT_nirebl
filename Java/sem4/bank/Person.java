package info.kgeorgiy.ja.tarasevich.bank;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

// :NOTE: Неплохо было бы разнести в отдельные package. Как минимум 4 класса связано с Person.
// :NOTE: Насколько я помню, после задания javadoc было требование, чтобы все публичные методы были документированы.
public interface Person extends Remote {
    String getFirstName() throws RemoteException;
    String getLastName() throws RemoteException;
    String getPassport() throws RemoteException;
    Account getAccount(String accountId) throws RemoteException;
}
