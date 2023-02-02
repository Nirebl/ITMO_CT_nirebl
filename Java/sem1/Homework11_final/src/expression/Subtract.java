package expression;

import java.math.BigInteger;

public class Subtract extends BinaryMultiExpressionAbstract {

    public Subtract(MultiExpressionElement first, MultiExpressionElement second) {
        super(10, "-", first, second);
    }

    @Override
    protected int calculate(int x, int y) {
        return x - y;
    }

    @Override
    protected BigInteger calculate(BigInteger x, BigInteger y) {
        return x.subtract(y);
    }

    @Override
    protected boolean isRequireRightBrackets(MultiExpressionElement element) {
        if (super.isRequireRightBrackets(element))
            return true;

        if (element instanceof BinaryMultiExpressionAbstract
                && second.getPriority() == this.getPriority()) {
            return true;
        }

        return false;
    }


}
