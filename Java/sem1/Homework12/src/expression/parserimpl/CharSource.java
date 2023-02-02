package expression.parserimpl;

import expression.parserimpl.exception.ExpressionException;

public interface CharSource {
    boolean hasNext();
    char next();
    ExpressionException error(final String message);
}
