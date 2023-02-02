package expression;

import java.math.BigInteger;

public class Divide extends BinaryMultiExpressionAbstract {
    public Divide(MultiExpressionElement first, MultiExpressionElement second) {
        super(100, "/", first, second);
    }

    @Override
    protected int calculate(int x, int y) {
        return x / y;
    }

    @Override
    protected BigInteger calculate(BigInteger x, BigInteger y) {
        return x.divide(y);
    }

    @Override
    protected boolean isRequireRightBrackets(MultiExpressionElement element) {
        return super.isRequireRightBrackets(element) || element instanceof BinaryMultiExpressionAbstract;
    }
}
