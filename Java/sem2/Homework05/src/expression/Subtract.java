package expression;

import expression.handler.Handler;

public class Subtract<T> extends AbstractBinaryExpression<T> {
    public Subtract(AbstractElement<T> first, AbstractElement<T> second)
    {
        super(first, second);
    }

    @Override
    protected T calculate(T x, T y, Handler<T> handler) {
        return handler.subtract(x, y);
    }

}
