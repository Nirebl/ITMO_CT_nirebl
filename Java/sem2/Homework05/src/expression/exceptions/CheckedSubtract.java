package expression.exceptions;

import expression.AbstractElement;
import expression.Subtract;


public class CheckedSubtract<T> extends Subtract<T> {
    public CheckedSubtract(AbstractElement<T> first, AbstractElement<T> second) {
        super(first, second);
    }
}
