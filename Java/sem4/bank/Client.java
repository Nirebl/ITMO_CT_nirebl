package info.kgeorgiy.ja.tarasevich.bank;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public final class Client {
    /** Utility class. */
    private Client() {}

    public static void main(final String... args) throws RemoteException {
        final Bank bank;
        try {
            bank = (Bank) Naming.lookup("//localhost/bank");
        } catch (final NotBoundException e) {
            System.out.println("Bank is not bound");
            return;
        } catch (final MalformedURLException e) {
            System.out.println("Bank URL is invalid");
            return;
        }

        final String firstName = args[0];
        final String lastName = args[1];
        final String passport = args[2];
        final String accountId = args[3];
        // :NOTE: в лицо пользователю полетит exception если не число. Плохо. Стектрейсы -- это не user-friendly.
        final int amount = Integer.parseInt(args[4]);
        // :NOTE: передавать тип числом без документации не user-friendly вообще.
        final int type = Integer.parseInt(args[5]);

        boolean personType = true;
        if (type == 0)
            personType = false;

        Person person  = bank.getPerson(passport, personType);
        if (person == null) {
            System.out.println("Creating person");
            person = bank.createPerson(passport, firstName, lastName);
        } else {
            System.out.println("Person already exists");
        }
        Account account = person.getAccount(accountId);

        System.out.println("Account id: " + account.getId());
        System.out.println("Money: " + account.getAmount());
        System.out.println("Adding money");
        account.setAmount(account.getAmount() + amount);
        System.out.println("Money: " + account.getAmount());
    }
}
