package info.kgeorgiy.ja.tarasevich.bank;

import info.kgeorgiy.ja.tarasevich.bank.exceptions.InvalidAccountIdException;
import info.kgeorgiy.ja.tarasevich.bank.exceptions.InvalidPersonPassport;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class RemoteBank implements Bank {
    private final int port;
    private final ConcurrentMap<String, PersonBase> persons = new ConcurrentHashMap<>();

    public RemoteBank(final int port) {
        this.port = port;
    }

    @Override
    public Person createPerson(String passport, String firstName, String lastName) throws RemoteException {
        System.out.println("Creating person " + passport + " " + firstName + " " + lastName);
        final PersonBase person = new RemotePerson(firstName, lastName, passport, port);
        if (persons.putIfAbsent(passport, person) == null) {
            UnicastRemoteObject.exportObject(person, port);
            return person;
        } else {
            return getPerson(passport, true);
        }
    }

    @Override
    public Person getPerson(String passport, Boolean remote) throws RemoteException {
        if (!persons.containsKey(passport)) {
            throw new InvalidPersonPassport("No person with passport " + passport);
        }

        if(remote)
            return persons.get(passport);
        else
            return new LocalPerson(persons.get(passport));
    }

    @Override
    public Account getAccount(final String accountId) throws RemoteException{
        System.out.println("Retrieving account " + accountId);

        if (accountId == null || accountId.isEmpty()) {
            throw new InvalidAccountIdException("Account id is null or empty");
        }

        String[] parts = accountId.split(":");
        if (parts.length != 2 || parts[0].isBlank() || parts[1].isBlank()) {
            throw new InvalidAccountIdException("Invalid account id");
        }

        if (persons.containsKey(parts[0])) {
            throw new InvalidAccountIdException("Invalid account id");
        }

        return persons.get(parts[0]).getAccount(accountId);
    }
}
