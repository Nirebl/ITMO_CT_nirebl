package expression.exceptions;

import expression.AbstractElement;
import expression.Multiply;

public class CheckedMultiply<T> extends Multiply<T> {
    public CheckedMultiply(AbstractElement<T> first, AbstractElement<T> second) {
        super(first, second);
    }
}
