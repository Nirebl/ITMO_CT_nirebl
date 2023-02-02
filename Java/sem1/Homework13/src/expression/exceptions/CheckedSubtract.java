package expression.exceptions;

import expression.MultiExpressionElement;
import expression.Subtract;

public class CheckedSubtract extends Subtract {
    public CheckedSubtract(MultiExpressionElement first, MultiExpressionElement second) {
        super(first, second);
    }

    @Override
    protected int calculate(int x, int y) {
        int res = super.calculate(x, y);

        if (x > 0 && y < 0 && res <= 0) {
            throw new OverflowException("overflow");
        }
        if (x < 0 && y > 0 && res >= 0) {
            throw new OverflowException("overflow");
        }
        if (x == 0 && y == Integer.MIN_VALUE) {
            throw new OverflowException("overflow");
        }
        return res;
    }

}
