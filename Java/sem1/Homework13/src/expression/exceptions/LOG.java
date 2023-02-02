package expression.exceptions;

import expression.BinaryMultiExpressionAbstract;
import expression.MultiExpressionElement;
import expression.Multiply;

import java.math.BigInteger;

public class LOG extends BinaryMultiExpressionAbstract {
    public LOG(MultiExpressionElement first, MultiExpressionElement second) {
        super(first, second);
    }

    @Override
    public int getPriority() {
        return 300;
    }

    @Override
    protected String getExpressionMark() {
        return "//";
    }

    @Override
    protected int calculate(int x, int y) {
  /*      if(x == 0 || y==0)
            throw new OverflowException("overflow");
*/
        if (x <= 0)
            throw new IncorrectArgumentValue("x <= 0");
        if (y <= 0)
            throw new IncorrectArgumentValue("y <= 0");
        if (y == 1)
            throw new IncorrectArgumentValue("y == 1");

        if (x == 1)
            return 0;
        if (x == y)
            return 1;
        int answer = 0;
        int pp = 1;

        while (pp <= x / y) {
            pp *= y;
            answer++;
            if (answer < 0) {
                throw new OverflowException("overflow");
            }
        }
        return answer;
    }

    @Override
    protected BigInteger calculate(BigInteger x, BigInteger y) {
        return x.add(y);
    }

    /*
        @Override
        protected boolean isRequireLeftBrackets(MultiExpressionElement element) {
            if (this.getPriority() == element.getPriority()) {
                if (element instanceof LOG)
                    return false;
                else
                    return true;
            }

            return super.isRequireLeftBrackets(element);
        }
    */
    @Override
    protected boolean isRequireRightBrackets(MultiExpressionElement element) {

        if (this.getPriority() + 5 == element.getPriority())
            return false;

        if (this.getPriority() == element.getPriority()) {
            if (element instanceof LOG)
                return true;
        }

        if (this.getPriority() == element.getPriority())
            return true;

        return super.isRequireRightBrackets(element);
    }

}
