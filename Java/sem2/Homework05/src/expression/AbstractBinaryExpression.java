package expression;

import expression.handler.Handler;

public abstract class AbstractBinaryExpression<T> extends AbstractElement<T> {
    private final AbstractElement<T> first, second;

    public AbstractBinaryExpression(AbstractElement<T> first, AbstractElement<T> second) {
        this.first = first;
        this.second = second;
    }

    protected abstract T calculate(T x, T y, Handler<T> handler);

    @Override
    public T evaluate(T x, T y, T z, Handler<T> handler){
        return calculate(first.evaluate(x, y, z, handler), second.evaluate(x, y, z, handler), handler);
    }

}
