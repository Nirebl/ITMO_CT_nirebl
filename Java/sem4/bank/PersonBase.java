package info.kgeorgiy.ja.tarasevich.bank;

import info.kgeorgiy.ja.tarasevich.bank.exceptions.InvalidAccountIdException;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public abstract class PersonBase implements Person, Serializable {
    protected final String firstName;
    protected final String lastName;
    protected final String passport;
    protected final ConcurrentMap<String, Account> accounts = new ConcurrentHashMap<>();

    public PersonBase(final String firstName, final String lastName, final String passport) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.passport = passport;
    }

    protected void addAccount(final Account account) {
        try {
            accounts.put(account.getId(), account);
        } catch (RemoteException e) {
            // :NOTE: не надо так делать.
            throw new RuntimeException(e);
        }
    }

    protected abstract Account createAccount(String accountId);

    @Override
    public String getFirstName() throws RemoteException {
        return firstName;
    }

    @Override
    public String getLastName() throws RemoteException {
        return lastName;
    }

    @Override
    public String getPassport() throws RemoteException {
        return passport;
    }

    @Override
    public Account getAccount(final String accountId) throws RemoteException {
        if (accountId == null || accountId.isEmpty()) {
            throw new InvalidAccountIdException("Account id is null or empty");
        }

        String[] parts = accountId.split(":");
        if (parts.length != 2 || parts[0].isBlank() || parts[1].isBlank()) {
            throw new InvalidAccountIdException("Invalid account id");
        }

        if (!Objects.equals(passport, parts[0])) {
            throw new InvalidAccountIdException("Invalid account id");
        }

        return accounts.computeIfAbsent(accountId, this::createAccount);
    }

}
