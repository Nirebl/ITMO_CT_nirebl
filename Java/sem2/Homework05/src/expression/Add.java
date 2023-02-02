package expression;

import expression.handler.Handler;

public class Add<T> extends AbstractBinaryExpression<T> {
    public Add(AbstractElement<T> first, AbstractElement<T> second) {
        super(first, second);
    }

    @Override
    protected T calculate(T x, T y, Handler<T> handler) {
        return handler.add(x, y);
    }

}
