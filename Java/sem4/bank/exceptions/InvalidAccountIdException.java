package info.kgeorgiy.ja.tarasevich.bank.exceptions;

import java.rmi.RemoteException;

public class InvalidAccountIdException extends RemoteException {
    public InvalidAccountIdException(String message) {
        super(message);
    }
}