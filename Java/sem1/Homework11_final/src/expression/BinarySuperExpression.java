package expression;

import java.math.BigInteger;
import java.util.Objects;

public abstract class BinarySuperExpression extends SuperExpressionImpl {

    private final SuperExpression first, second;

    public BinarySuperExpression(SuperExpression first, SuperExpression second) {
        this.first = first;
        this.second = second;
    }

    protected abstract int calculate(int x, int y);
    protected abstract BigInteger calculate(BigInteger x, BigInteger y);

    protected abstract String getExpressionMark();

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

    private boolean checkPriority(SuperExpression exp) {
        return exp.getPriority1() > this.getPriority();
    }

    private void termToString(StringBuilder sb, SuperExpression exp, boolean brackets) {
        if (brackets) {
            sb.append("(").append(exp.toMiniString()).append(")");
        } else {
            sb.append(exp.toMiniString());
        }
    }

    private boolean requireBrackets(SuperExpression exp) {
        return second.getPriority1() < this.getPriority() ||
                second.getPriority1() == this.getPriority() &&
                        (second.isRequired1() || this.isRequired());
    }

    @Override
    public String toMiniString() {

        StringBuilder sb = new StringBuilder();

        SuperExpression exp = this.first;
        //termToString(sb, exp, checkPriority(exp));
        termToString(sb, exp, requireBrackets(first));
        sb.append(" ").append(getExpressionMark()).append(" ");
        termToString(sb, second, requireBrackets(second));

        return sb.toString();
    }

    @Override
    public String toString() {
        return "(" + first + " " + getExpressionMark() + " " + second + ")";
    }

    @Override
    public int hashCode() {
        return Objects.hash(first, second, this.getClass());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof BinarySuperExpression) {
            BinarySuperExpression newSecond = (BinarySuperExpression) obj;
            return Objects.equals(this.getClass(), obj.getClass()) &&
                    Objects.equals(first, newSecond.first) && Objects.equals(second, newSecond.second);
        } else {
            return false;
        }
    }

}
