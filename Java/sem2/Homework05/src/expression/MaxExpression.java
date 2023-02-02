package expression;

import expression.handler.Handler;

public class MaxExpression<T> extends AbstractBinaryExpression<T> {
    public MaxExpression(AbstractElement<T> first, AbstractElement<T> second) {
        super(first, second);
    }

    @Override
    protected T calculate(T x, T y, Handler<T> handler) {
        return handler.max(x, y);
    }


}
