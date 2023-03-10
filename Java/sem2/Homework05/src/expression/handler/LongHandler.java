package expression.handler;

import expression.exceptions.DivideByZeroException;

public class LongHandler implements Handler<Long>{
    @Override
    public Long cast(int value) {
        return (long) value;
    }

    @Override
    public Long parse(String value) {
        return Long.parseLong(value);
    }

    @Override
    public Long add(Long x, Long y) {
        return x + y;
    }

    @Override
    public Long subtract(Long x, Long y) {
        return x - y;
    }

    @Override
    public Long multiply(Long x, Long y) {
        return x * y;
    }

    @Override
    public Long divide(Long x, Long y) throws DivideByZeroException{
        if (y == 0) {
            throw new DivideByZeroException();
        }
        return x / y;
    }

    @Override
    public Long max(Long x, Long y) {
        return Math.max(x, y);
    }

    @Override
    public Long min(Long x, Long y) {
        return Math.min(x, y);
    }

    @Override
    public Long negate(Long x) {
        return -x;
    }

    @Override
    public Long count(Long x) {
        return (long) Long.bitCount(x);
    }

}
