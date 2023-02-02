package expression.exceptions;

import expression.MultiExpressionElement;
import expression.UnaryMinus;

public class CheckedNegate extends UnaryMinus {
    public CheckedNegate(MultiExpressionElement expressionElement) {
        super(expressionElement);
    }

    @Override
    public int evaluate(int x) {
        if (x == Integer.MIN_VALUE) {
            throw new OverflowException("overflow");
        }

        return super.evaluate(x);
    }

    @Override
    public int evaluate(int x, int y, int z) {
        int res = super.calculate(x, y, z);

        if (res == Integer.MIN_VALUE){
            throw new OverflowException("overflow");
        }

        return -res;
    }

}
