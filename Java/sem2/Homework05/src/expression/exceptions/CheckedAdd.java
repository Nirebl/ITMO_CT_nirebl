package expression.exceptions;

import expression.AbstractElement;
import expression.Add;

public class CheckedAdd<T> extends Add<T> {
    public CheckedAdd(AbstractElement<T> first, AbstractElement<T> second) {
        super(first, second);
    }
}
