package info.kgeorgiy.ja.tarasevich.bank.exceptions;

import java.rmi.RemoteException;

public class InvalidPersonPassport extends RemoteException {
    public InvalidPersonPassport(String message) {
        super(message);
    }
}