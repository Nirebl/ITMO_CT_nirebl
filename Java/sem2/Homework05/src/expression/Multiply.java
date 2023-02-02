package expression;

import expression.handler.Handler;

public class Multiply<T> extends AbstractBinaryExpression<T> {
    public Multiply(AbstractElement<T> first, AbstractElement<T> second)
    {
        super(first, second);
    }

    @Override
    protected T calculate(T x, T y, Handler<T> handler) {
        return handler.multiply(x, y);
    }

}
