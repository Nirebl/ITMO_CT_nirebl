package expression;

import expression.handler.Handler;

public final class Const<T> extends AbstractElement<T>{
    private final String value;

    public Const(int value) {
        this.value = String.valueOf(value);
    }

    public Const(String value) {
        this.value = value;
    }

    @Override
    public T evaluate(T x, T y, T z, Handler<T> handler) {
        return handler.parse(value);
    }
}
