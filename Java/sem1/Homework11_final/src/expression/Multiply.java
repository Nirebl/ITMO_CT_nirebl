package expression;

import java.math.BigInteger;

public class Multiply extends BinaryMultiExpressionAbstract {

    public Multiply(MultiExpressionElement first, MultiExpressionElement second) {
        super(100, "*", first, second);
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

        // :NOTE: Анализ подтипов
        if (element instanceof BinaryMultiExpressionAbstract
                && !(element instanceof Multiply)
                && element.getPriority() == this.getPriority()) {
            return true;
        }

        return false;
    }

}
