package expression;

import expression.handler.Handler;

public class MinExpression<T> extends AbstractBinaryExpression<T> {
    public MinExpression(AbstractElement<T> first, AbstractElement<T> second) {
        super(first, second);
    }

    @Override
    protected T calculate(T x, T y, Handler<T> handler) {
        return handler.min(x, y);
    }
}