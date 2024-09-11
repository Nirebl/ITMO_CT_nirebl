package info.kgeorgiy.ja.tarasevich.bank;

import java.io.Serializable;

public abstract class AccountBase implements Account, Serializable {
    private final String id;
    // :NOTE: int может оказаться маловат
    private int amount;

    public AccountBase(final String id) {
        this.id = id;
        amount = 0;
    }

    public AccountBase(final String id, final int amount) {
        this.id = id;
        this.amount = amount;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public synchronized int getAmount() {
        System.out.println("Getting amount of money for account " + id);
        return amount;
    }

    @Override
    public synchronized void setAmount(final int amount) {
        System.out.println("Setting amount of money for account " + id);
        this.amount = amount;
    }

}
