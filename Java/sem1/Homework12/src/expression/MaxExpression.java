package expression;

import java.math.BigInteger;

public class MaxExpression extends BinaryMultiExpressionAbstract {
    public MaxExpression(MultiExpressionElement first, MultiExpressionElement second) {
        super(first, second);
    }

    @Override
    public int getPriority() {
        return 5;
    }

    @Override
    protected String getExpressionMark() {
        return "max";
    }

    @Override
    protected int calculate(int x, int y) {
        return x > y ? x : y;
    }

    @Override
    protected BigInteger calculate(BigInteger x, BigInteger y) {
        return x.max(y);
    }

}
