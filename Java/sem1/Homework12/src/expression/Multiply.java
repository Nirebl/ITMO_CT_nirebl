package expression;

import java.math.BigInteger;

public class Multiply extends BinaryMultiExpressionAbstract {

    public Multiply(MultiExpressionElement first, MultiExpressionElement second) {
        super(first, second);
    }

    @Override
    public int getPriority() {
        return 100;
    }

    @Override
    protected String getExpressionMark() {
        return "*";
    }

    @Override
    protected int calculate(int x, int y) {
        return x * y;
    }

    @Override
    protected BigInteger calculate(BigInteger x, BigInteger y) {
        return x.multiply(y);
    }

    @Override
    protected boolean isRequireRightBrackets(MultiExpressionElement element) {
        if (super.isRequireRightBrackets(element))
            return true;

        return element.getPriority() == this.getPriority()
                && !(element instanceof Multiply);
    }

}
