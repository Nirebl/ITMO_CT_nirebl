package info.kgeorgiy.ja.tarasevich.bank.tests;

import info.kgeorgiy.ja.tarasevich.bank.Account;
import info.kgeorgiy.ja.tarasevich.bank.Bank;
import info.kgeorgiy.ja.tarasevich.bank.Person;
import info.kgeorgiy.ja.tarasevich.bank.RemoteBank;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

// :NOTE: Тестов очень мало: 3 теста всего. Нет тестов на клиент/сервер, нет многопоточных тестов.
// :NOTE: Нет тестов на взаимодействие двух Remote Person, двух Local Person.
// Есть логика валидации приходящего id, но нет негативных сценариев.

// :NOTE: нет каталога lib в твоем репозитории. По идее это вообще не компилируется.
// :NOTE: также на паре НВ просил, чтобы были скрипты для запуска тестов, сервера и клиента: их нет. Если бы были, то ты заметил бы отсутствие lib
public class RemotePersonTest {
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
    void createRemotePersonTest() throws RemoteException {
        Person person = bank.createPerson("newPass", "Ivan", "Ivanov");
        assertNotNull(person);
    }

    @Test
    void createRemoteAccountTest() throws RemoteException {
        Person person = bank.createPerson("newPass1", "Ivan1", "Ivanov1");
        assertNotNull(person);

        Account account = person.getAccount("newPass1:new");
        assertNotNull(account);

        account.setAmount(50);
        var sum = account.getAmount();

        assertEquals(50, sum);
    }

}
