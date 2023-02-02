package expression;

import java.math.BigInteger;
import java.util.Objects;

public class UnaryMinus extends MultiExpressionElement {
    private final MultiExpressionElement expressionElement;

    public UnaryMinus(MultiExpressionElement expressionElement) {
        this.expressionElement = expressionElement;
    }

    @Override
    public int getPriority() {
        return 200;
    }

    @Override
    public int evaluate(int x) {
        return -expressionElement.evaluate(x);
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return -expressionElement.evaluate(x, y, z);
    }

    @Override
    public BigInteger evaluate(BigInteger x) {
        return expressionElement.evaluate(x).negate();
    }

    @Override
    public String toMiniString() {
        StringBuilder sb = new StringBuilder();

        if(expressionElement.getPriority() >= this.getPriority()) {
            sb.append("- ").append(expressionElement.toMiniString());
        } else {
            sb.append("-(").append(expressionElement.toMiniString()).append(")");
        }

        return sb.toString();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("-(").append(expressionElement.toString()).append(")");

        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof UnaryMinus second) {
            return expressionElement.equals(second.expressionElement);
        }

        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash("-", expressionElement.hashCode(), this.getClass());
    }

}
