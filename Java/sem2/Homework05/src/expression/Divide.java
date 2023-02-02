package expression;

import expression.handler.Handler;

public class Divide<T> extends AbstractBinaryExpression<T> {
    public Divide(AbstractElement<T> first, AbstractElement<T> second) {
        super(first, second);
    }

    @Override
    protected T calculate(T x, T y, Handler<T> handler) {
        return handler.divide(x, y);
    }

}
