package expression;

import java.math.BigInteger;

public class Add extends BinaryMultiExpressionAbstract {
    public Add(MultiExpressionElement first, MultiExpressionElement second) {
        super(10, "+", first, second);
    }

    @Override
    protected int calculate(int x, int y) {
        return x + y;
    }

    @Override
    protected BigInteger calculate(BigInteger x, BigInteger y) {
        return x.add(y);
    }

}
