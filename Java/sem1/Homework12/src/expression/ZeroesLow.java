package expression;

import java.math.BigInteger;
import java.util.Objects;

public class ZeroesLow extends MultiExpressionElement {
    private final MultiExpressionElement value;

    public ZeroesLow(MultiExpressionElement value) {
        this.value = value;
    }

    @Override
    public int getPriority() {
        return 200;
    }

    @Override
    public int evaluate(int x) {
        return Integer.numberOfTrailingZeros(value.evaluate(x));
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return Integer.numberOfTrailingZeros(value.evaluate(x, y, z));
    }

    @Override
    public BigInteger evaluate(BigInteger x) {
        throw new UnsupportedOperationException("t0 unsupported BigInteger");
    }

    @Override
    public String toString() {
        return "t0(" + value.toString() + ")";
    }

    @Override
    public String toMiniString() {
        if (this.getPriority() > value.getPriority())
            return "t0(" + value.toMiniString() + ")";
        else
            return "t0 " + value.toMiniString();
    }


    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ZeroesLow second) {
            return value == second.value;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash("t0", value.hashCode(), this.getClass());
    }
}
