package expression.exceptions;

import expression.AbstractElement;
import expression.Divide;

public class CheckedDivide<T> extends Divide<T>{
    public CheckedDivide(AbstractElement<T> first, AbstractElement<T> second) {
        super(first, second);
    }
}
