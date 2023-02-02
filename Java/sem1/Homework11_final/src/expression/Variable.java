package expression;

import java.util.Objects;
import java.math.BigInteger;
import java.util.NoSuchElementException;

public class Variable extends MultiExpressionElement {
    private final String var;

    public Variable(String var) {
        super(1000);
        this.var = var;
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
        if (obj instanceof Variable) {
            Variable second = (Variable) obj;
            return var.equals(second.var);
        }

        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(var);
    }

}
