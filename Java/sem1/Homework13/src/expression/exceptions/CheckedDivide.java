package expression.exceptions;

import expression.Divide;
import expression.MultiExpressionElement;

public class CheckedDivide extends Divide {
    public CheckedDivide(MultiExpressionElement first, MultiExpressionElement second) {
        super(first, second);
    }

    @Override
    protected int calculate(int x, int y) {

        if (y == 0) {
            throw new DivideByZeroException("division by zero");
        }
        if (y == -1 && x == Integer.MIN_VALUE){
            throw new OverflowException("overflow");
        }

        return super.calculate(x, y);
    }

}
