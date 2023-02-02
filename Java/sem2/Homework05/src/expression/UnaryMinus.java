package expression;

import expression.handler.Handler;

public class UnaryMinus<T> extends AbstractUnaryExpression<T>{

    public UnaryMinus(AbstractElement<T> element) {
        super (element);
    }

    @Override
    protected T calculate(T x, Handler<T> handler) {
        return handler.negate(x);
    }

}
