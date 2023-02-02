package expression.exceptions;

import expression.Add;
import expression.MultiExpressionElement;

public class CheckedAdd extends Add {
    public CheckedAdd(MultiExpressionElement first, MultiExpressionElement second) {
        super(first, second);
    }

    @Override
    protected int calculate(int x, int y) {
        int res = super.calculate(x, y);

        if (y > 0 && x > 0 && res <= 0) {
            throw new OverflowException("overflow");
        }
        if (y < 0 && x < 0 && res >= 0) {
            throw new OverflowException("overflow");
        }
        return res;
    }

}
