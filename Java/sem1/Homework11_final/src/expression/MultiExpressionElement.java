package expression;

public abstract class MultiExpressionElement implements MultiExpression {
    // :NOTE: Убрать
    private final int priority;

    public MultiExpressionElement(int priority) {
        this.priority = priority;
    }

    protected int getPriority() {
        return priority;
    }

}
