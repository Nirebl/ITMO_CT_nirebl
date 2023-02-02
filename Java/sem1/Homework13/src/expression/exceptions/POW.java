package expression.exceptions;

import expression.BinaryMultiExpressionAbstract;
import expression.MultiExpressionElement;
import expression.Multiply;

import java.math.BigInteger;
//import java.math.Math;

public class POW extends BinaryMultiExpressionAbstract {
    public POW(MultiExpressionElement first, MultiExpressionElement second) {
        super(first, second);
    }

    @Override
    public int getPriority() {
        return 300;
    }

    @Override
    protected String getExpressionMark() {
        return "**";
    }

    @Override
    protected int calculate(int x, int y) {
        if (x == 0 && y == 0)
            throw new OverflowException("overflow");

        if (y < 0)
            throw new OverflowException("overflow " + y);

        if (y == 0)
            return 1;

        if (x == 0)
            return 0;

        int res = 1;

        if(x==-2147483648 && y==1)
        {
            System.err.println('0');
        }
        while (y > 0) {
            if ((y % 2) == 1) {
                int test = Integer.MIN_VALUE / res / x;//(long) res * (long) x;
                if (test == 0) {
                    throw new OverflowException("overflow");
                }
                res *= x;
            }

            int test = Integer.MIN_VALUE / x / x;
            if (test == 0 && y > 1) {
                throw new OverflowException("overflow");
            }
            x *= x;
            y >>= 1;
        }

        return res;

/*
        int res = x;
        int prev;

        for (int i = 1; i < y; ++i) {
            prev = res;
            res *= x;
            if (prev != res / x)
                throw new OverflowException("overflow");
        }
*/

    }

    @Override
    protected BigInteger calculate(BigInteger x, BigInteger y) {
        return x.add(y);
    }

    /*
    @Override
    protected boolean isRequireLeftBrackets(MultiExpressionElement element) {
        if(this.getPriority() - 10 == element.getPriority())
            return false;


        return super.isRequireLeftBrackets(element);
    }*/

    @Override
    protected boolean isRequireRightBrackets(MultiExpressionElement element) {
        if (super.isRequireRightBrackets(element))
            return true;

        return element.getPriority() == this.getPriority();
        //&& (element instanceof POW);
    }

}
