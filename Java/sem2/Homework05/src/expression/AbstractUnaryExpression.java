package expression;

import expression.handler.Handler;

public abstract class AbstractUnaryExpression<T> extends AbstractElement<T> {
    private final AbstractElement<T> element;

    public AbstractUnaryExpression(AbstractElement<T> element) {
        this.element = element;
    }

    protected abstract T calculate(T x, Handler<T> handler);

    @Override
    public T evaluate(T x, T y, T z, Handler<T> handler) {
        return calculate(element.evaluate(x, y, z, handler), handler);
    }
}
