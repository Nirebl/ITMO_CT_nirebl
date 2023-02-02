package expression.exceptions;

import expression.MultiExpressionElement;
import expression.Multiply;

public class CheckedMultiply extends Multiply {
    public CheckedMultiply(MultiExpressionElement first, MultiExpressionElement second) {
        super(first, second);
    }

    @Override
    protected int calculate(int x, int y) {
        int res = super.calculate(x, y);

        if (x != 0 && y != 0 && (res / y != x || res / x != y)){
            throw new OverflowException("overflow");
        }
        return res;
    }

}
