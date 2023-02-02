package expression;

import expression.handler.Handler;


public class Count<T> extends AbstractUnaryExpression<T> {

    public Count(AbstractElement<T> element) {
        super(element);
    }

    @Override
    protected T calculate(T x, Handler<T> handler) {
        return handler.count(x);
    }

}
