package info.kgeorgiy.ja.tarasevich.bank.tests;

import info.kgeorgiy.ja.tarasevich.bank.Account;
import info.kgeorgiy.ja.tarasevich.bank.Bank;
import info.kgeorgiy.ja.tarasevich.bank.Person;
import info.kgeorgiy.ja.tarasevich.bank.RemoteBank;
import org.junit.jupiter.api.*;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class LocalPeronTest {
    private final static int PORT = 8807;

    private static Bank bank;
    private static Registry registry;

    @BeforeAll
    static void beforeAll() {
        try {
            registry = LocateRegistry.createRegistry(PORT);
            UnicastRemoteObject.unexportObject(registry, true);
            Bank b = new RemoteBank(PORT);
            var stub = UnicastRemoteObject.exportObject(b, PORT);
            registry.rebind("//localhost/bank", stub);
            bank = (Bank) registry.lookup("//localhost/bank");

        } catch (RemoteException | NotBoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void validateRemotevsLoaclTest() throws RemoteException {
        Person remotePerson = bank.createPerson("newPass", "Ivan", "Ivanov");
        assertNotNull(remotePerson);

        Account remoteAccount = remotePerson.getAccount("newPass:new");
        assertNotNull(remoteAccount);

        remoteAccount.setAmount(50);
        assertEquals(50, remoteAccount.getAmount());

        Person localPerson = bank.getPerson("newPass", false);
        assertNotNull(localPerson);

        Account localAccount = localPerson.getAccount("newPass:new");
        assertNotNull(localAccount);
        assertEquals(50, localAccount.getAmount());

        remoteAccount.setAmount(85);
        assertEquals(85, remoteAccount.getAmount());

        assertEquals(50, localAccount.getAmount());
        localAccount.setAmount(1050);
        assertEquals(1050, localAccount.getAmount());
        assertEquals(85, remoteAccount.getAmount());


    }

}
