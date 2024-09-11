package info.kgeorgiy.ja.tarasevich.bank;

import java.io.Serializable;
import java.rmi.RemoteException;

public class LocalAccount extends AccountBase implements Account, Serializable {

    // :NOTE: а он точно кидает RemoteException?
    public LocalAccount(final String id, final int amount) throws RemoteException {
        super(id, amount);
    }
}
