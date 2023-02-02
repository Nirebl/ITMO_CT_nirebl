package expression.handler;

import expression.exceptions.DivideByZeroException;

public class ShortHandler implements Handler<Short>{

    @Override
    public Short cast(int value) {
        return (short) value;
    }

    @Override
    public Short parse(String value) {
        return (short) Integer.parseInt(value);
    }

    @Override
    public Short add(Short x, Short y) {
        return (short) (x + y);
    }

    @Override
    public Short subtract(Short x, Short y) {
        return (short) (x - y);
    }

    @Override
    public Short multiply(Short x, Short y) {
        return (short) (x * y);
    }

    @Override
    public Short divide(Short x, Short y) throws DivideByZeroException{
        if (y == 0) {
            throw new DivideByZeroException();
        }
        return (short) (x / y);
    }

    @Override
    public Short max(Short x, Short y) {
        return (short) Math.max(x, y);
    }

    @Override
    public Short min(Short x, Short y) {
        return (short) Math.min(x, y);
    }

    @Override
    public Short negate(Short x) {
        return (short) (-x);
    }

    @Override
    public Short count(Short x) {
        return x == -1 ? 16 : (short) (Integer.bitCount(x) % 16);
    }

}
