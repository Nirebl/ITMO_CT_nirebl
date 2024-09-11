package info.kgeorgiy.ja.tarasevich.bank;

import java.io.Serializable;
import java.rmi.RemoteException;

public class LocalPerson extends PersonBase implements Person, Serializable {

    public LocalPerson(final PersonBase person) {
        super(person.firstName, person.lastName, person.passport);

        person.accounts.forEach( (key, value) -> {
            try {
                super.addAccount(new LocalAccount(key, value.getAmount()));
            } catch (RemoteException e) {
                // :NOTE: В этой имплементации ничто не должно кидать RemoteException
                throw new RuntimeException(e);
            }
        });
    }


    @Override
    protected Account createAccount(String accountId) {
        try {
            return new LocalAccount(accountId, 0);
        } catch (RemoteException e) {
            // :NOTE: В этой имплементации ничто не должно кидать RemoteException
            throw new RuntimeException(e);
        }
    }
}
