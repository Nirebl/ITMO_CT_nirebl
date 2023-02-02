package expression;

import java.util.Objects;
import java.math.BigInteger;
import java.util.NoSuchElementException;

public class Variable extends MultiExpressionElement {
    private final String var;

    public Variable(String var) {
        this.var = var;
    }

    @Override
    public int getPriority() {
        return 1000;
    }

    @Override
    public int evaluate(int x) {
        return x;
    }

    @Override
    public int evaluate(int x, int y, int z) {
        switch (var) {
            case "x":
                return x;
            case "y":
                return y;
            case "z":
                return z;
        }
        throw new NoSuchElementException("Incorrect variable");
    }

    @Override
    public BigInteger evaluate(BigInteger x) {
        return x;
    }

    @Override
    public String toString() {
        return var;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Variable second) {
            return var.equals(second.var);
        }

        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(var);
    }

}
