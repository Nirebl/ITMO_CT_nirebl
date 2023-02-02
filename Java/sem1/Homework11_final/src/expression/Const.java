package expression;

import java.math.BigInteger;
import java.util.Objects;

public class Const extends MultiExpressionElement {
    private final int value;
    private final BigInteger bgValue;
    private final boolean isBigInteger;

    public Const(int value) {
        super(1000);
        this.value = value;
        this.bgValue = BigInteger.valueOf(value);
        this.isBigInteger = false;
    }

    public Const(BigInteger bgValue) {
        super(1000);
        this.bgValue = bgValue;
        this.value = bgValue.intValue();
        this.isBigInteger = true;
    }

    @Override
    public int getPriority() {
        return 1000;
    }

    @Override
    public int evaluate(int x) {
        if (isBigInteger)
            return bgValue.intValue();
        else
            return value;
    }

    @Override
    public int evaluate(int x, int y, int z) {
        if (isBigInteger)
            return bgValue.intValue();
        else
            return value;
    }

    @Override
    public BigInteger evaluate(BigInteger x) {
        if (isBigInteger)
            return bgValue;
        else
            return BigInteger.valueOf(value);
    }

    @Override
    public String toString() {
        if (isBigInteger)
            return bgValue.toString();
        else
            return Integer.toString(value);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Const second) {
            if (!isBigInteger)
                return value == second.value;
            else
                return Objects.equals(bgValue, second.bgValue);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        if (!isBigInteger)
            return Integer.hashCode(value);
        else
            return bgValue.hashCode();
    }

}
