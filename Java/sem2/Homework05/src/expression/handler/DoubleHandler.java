package expression.handler;


import expression.exceptions.DivideByZeroException;
import expression.exceptions.OverflowException;

public final class DoubleHandler implements Handler<Double> {

    @Override
    public Double cast(int value) {
        return (double) value;
    }

    @Override
    public Double parse(String value) {
        return Double.parseDouble(value);
    }

    @Override
    public Double add(Double x, Double y) {
        return x + y;
    }

    @Override
    public Double subtract(Double x, Double y) {
        return x - y;
    }

    @Override
    public Double multiply(Double x, Double y) {
        return x * y;
    }

    @Override
    public Double divide(Double x, Double y) throws DivideByZeroException {
        /*if (y == 0) {
            throw new DivideByZeroException();
        }
*/
        return x / y;
    }

    @Override
    public Double max(Double x, Double y) {
        return Math.max(x, y);
    }

    @Override
    public Double min(Double x, Double y) {
        return Math.min(x, y);
    }

    @Override
    public Double negate(Double x) {
        return -x;
    }

    @Override
    public Double count(Double x) {
        return (double) Long.bitCount(Double.doubleToLongBits(x));
    }

}
