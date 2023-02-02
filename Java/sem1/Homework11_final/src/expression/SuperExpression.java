package expression;

public interface SuperExpression extends Expression, TripleExpression, BigIntegerExpression {
    int getPriority1();
    boolean isRequired1();
}
