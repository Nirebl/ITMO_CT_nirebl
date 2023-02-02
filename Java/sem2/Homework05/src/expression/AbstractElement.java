package expression;

import expression.handler.Handler;

public abstract class AbstractElement<T> implements TripleExpression, Expression {

    @Override
    public int evaluate(int x, int y, int z)  {
        return 0;
    }

    @Override
    public int evaluate(int x) {
        return 0;
    }

    public abstract T evaluate(T x, T y, T z, Handler<T> handler);


}
