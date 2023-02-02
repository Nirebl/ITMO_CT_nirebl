package expression.exceptions;

import expression.MultiExpressionElement;

import java.math.BigInteger;

public class ABS  extends MultiExpressionElement {
    private final MultiExpressionElement value;

    public ABS(MultiExpressionElement value) {
        this.value = value;
    }

    private int getAbs(int x) {
        if (x == Integer.MIN_VALUE){
            throw new OverflowException("overflow");
        }

        return (x < 0 ? -x : x);
    }

    @Override
    public int getPriority() {
        return 400;
    }

    @Override
    public int evaluate(int x) {
        return getAbs(value.evaluate(x));
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return getAbs(value.evaluate(x,y,z));
    }

    @Override
    public BigInteger evaluate(BigInteger x) {
        throw new java.lang.UnsupportedOperationException("t0 unsupported BigInteger");
    }

    @Override
    public String toMiniString() {
        if (this.getPriority() > value.getPriority())
            return "abs(" + value.toMiniString() + ")";
        else
            return "abs " + value.toMiniString();
    }
    public String toString() {
        return "abs("+ value.toString()+")";
    }

}
