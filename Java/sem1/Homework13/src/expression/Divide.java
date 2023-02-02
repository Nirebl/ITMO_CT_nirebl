package expression;

import java.math.BigInteger;

public class Divide extends BinaryMultiExpressionAbstract {
    public Divide(MultiExpressionElement first, MultiExpressionElement second) {
        super(first, second);
    }

    @Override
    public int getPriority() {
        return 100;
    }

    @Override
    protected String getExpressionMark() {
        return "/";
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

        if (super.isRequireRightBrackets(element))
            return true;

        return element.getPriority() == this.getPriority();

        /*
        if(this.getPriority() == element.getPriority()
                &&  !(element instanceof Divide))
            return false;


         */
            /*&&
                !(element instanceof Divide))
            return true;


             */
/*
        if ( (element.getPriority() == this.getPriority()) ||
                (this.getPriority() + 10 == element.getPriority()))
                return true;

        return super.isRequireRightBrackets(element);

 */
    }


}
