package expression.exceptions;

import expression.AbstractElement;
import expression.handler.Handler;

public class CheckedNegate<T> extends AbstractElement<T> {
    public CheckedNegate(AbstractElement<T> element) {
    }

    @Override
    public T evaluate(T x, T y, T z, Handler<T> handler) {
        return null;
    }
}
