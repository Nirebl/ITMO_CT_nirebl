package expression.handler;

import expression.exceptions.DivideByZeroException;

public class IntHandler implements Handler<Integer>{

    @Override
    public Integer cast(int value) {
        return value;
    }

    @Override
    public Integer parse(String value) {
        return Integer.parseInt(value);
    }

    @Override
    public Integer add(Integer x, Integer y) {
        return x + y;
    }

    @Override
    public Integer subtract(Integer x, Integer y) {
        return x - y;
    }

    @Override
    public Integer multiply(Integer x, Integer y) {
        return x * y;
    }

    @Override
    public Integer divide(Integer x, Integer y) throws DivideByZeroException{
        if (y == 0) {
            throw new DivideByZeroException();
        }
        return x / y;
    }

    @Override
    public Integer max(Integer x, Integer y) {
        return Math.max(x, y);
    }

    @Override
    public Integer min(Integer x, Integer y) {
        return Math.min(x, y);
    }

    @Override
    public Integer negate(Integer x) {
        return -x;
    }

    @Override
    public Integer count(Integer x) {
        return Integer.bitCount(x);
    }

}
