package expression.handler;

import expression.exceptions.DivideByZeroException;

import java.math.BigInteger;

public final class BigIntegerHandler implements Handler<BigInteger>{
    @Override
    public BigInteger cast(int value)
    {
        return BigInteger.valueOf(value);
    }

    @Override
    public BigInteger parse(String value) {
        return new BigInteger(value);
    }

    @Override
    public BigInteger add(BigInteger x, BigInteger y) {
        return x.add(y);
    }

    @Override
    public BigInteger subtract(BigInteger x, BigInteger y) {
        return x.subtract(y);
    }

    @Override
    public BigInteger multiply(BigInteger x, BigInteger y) {
        return x.multiply(y);
    }

    @Override
    public BigInteger divide(BigInteger x, BigInteger y) throws DivideByZeroException {
        if (y.compareTo(BigInteger.ZERO) == 0) {
            throw new DivideByZeroException();
        }
        return x.divide(y);
    }

    @Override
    public BigInteger max(BigInteger x, BigInteger y) {
        return x.max(y);
    }

    @Override
    public BigInteger min(BigInteger x, BigInteger y) {
        return x.min(y);
    }

    @Override
    public BigInteger negate(BigInteger x) {
        return x.negate();
    }

    @Override
    public BigInteger count(BigInteger x) {
        return BigInteger.valueOf(x.bitCount());
    }

}
