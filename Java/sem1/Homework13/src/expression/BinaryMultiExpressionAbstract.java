package expression;

import java.math.BigInteger;
import java.util.Objects;

public abstract class BinaryMultiExpressionAbstract extends MultiExpressionElement {
    private final MultiExpressionElement first, second;

    public BinaryMultiExpressionAbstract(MultiExpressionElement first, MultiExpressionElement second) {
        this.first = first;
        this.second = second;
    }

    protected abstract String getExpressionMark();

    protected abstract int calculate(int x, int y);

    protected abstract BigInteger calculate(BigInteger x, BigInteger y);

    @Override
    public int evaluate(int x) {
        return calculate(first.evaluate(x), second.evaluate(x));
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return calculate(first.evaluate(x, y, z), second.evaluate(x, y, z));
    }

    @Override
    public BigInteger evaluate(BigInteger x) {
        return calculate(first.evaluate(x), second.evaluate(x));
    }

    private boolean checkPriority(MultiExpressionElement element) {
        return element.getPriority() < this.getPriority();
    }

    private void appendExpressionToMiniString(StringBuilder sb, MultiExpressionElement exp, boolean brackets) {
        if (brackets) {
            sb.append("(").append(exp.toMiniString()).append(")");
        } else {
            sb.append(exp.toMiniString());
        }
    }

    protected boolean isRequireLeftBrackets(MultiExpressionElement element) {
        return checkPriority(element);
    }

    protected boolean isRequireRightBrackets(MultiExpressionElement element) {
        return checkPriority(element);
    }

    @Override
    public String toMiniString() {
        StringBuilder sb = new StringBuilder();

        appendExpressionToMiniString(sb, first, isRequireLeftBrackets(first));
        sb.append(" ").append(getExpressionMark()).append(" ");
        appendExpressionToMiniString(sb, second, isRequireRightBrackets(second));

        return sb.toString();
    }

    @Override
    public String toString() {
        return "(" + first + " " + getExpressionMark() + " " + second + ")";
    }

    @Override
    public int hashCode() {
        return Objects.hash(first.hashCode(), second.hashCode(), this.getClass());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof BinaryMultiExpressionAbstract) {
            BinaryMultiExpressionAbstract compared = (BinaryMultiExpressionAbstract) obj;

            return Objects.equals(this.getClass(), obj.getClass()) &&
                    first.equals(compared.first) && second.equals(compared.second);

        } else {
            return false;
        }
    }

}
