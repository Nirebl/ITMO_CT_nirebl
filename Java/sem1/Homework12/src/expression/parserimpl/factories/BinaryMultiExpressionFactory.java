package expression.parserimpl.factories;

import expression.*;
import expression.parserimpl.exception.ExpressionException;

public class BinaryMultiExpressionFactory {
    private BinaryMultiExpressionFactory(){}

    public static MultiExpressionElement Create(String expressionMark, MultiExpressionElement left, MultiExpressionElement right)
    {
        return switch (expressionMark) {
            case "+" -> new Add(left, right);
            case "-" -> new Subtract(left, right);
            case "*" -> new Multiply(left, right);
            case "/" -> new Divide(left, right);
            case "min" -> new MinExpression(left, right);
            case "max" -> new MaxExpression(left, right);
            default -> throw new IllegalArgumentException("Unsupported operator found :" + expressionMark);
        };
    }

}
