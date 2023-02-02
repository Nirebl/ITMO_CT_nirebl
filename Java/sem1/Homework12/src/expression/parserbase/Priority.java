package expression.parserbase;

import java.util.Map;

public final class Priority {
    private static final int stepPriority = 10;
    private static final int lowestPriority = 0;
    private static final int highestPriority = 30;

    private static final int multiplyPriority = 10;
    private static final int dividePriority = 10;
    private static final int addPriority = 20;
    private static final int subtractPriority = 20;
    private static final int minPriority = 30;
    private static final int maxPriority = 30;

    private static final int rightBracketPriority = highestPriority + stepPriority;

    public static final Priority Lowest = new Priority(lowestPriority);
    public static final Priority Highest = new Priority(highestPriority);

    public static final Priority Multiply = new Priority(multiplyPriority);
    public static final Priority Divide = new Priority(dividePriority);
    public static final Priority Add = new Priority(addPriority);
    public static final Priority Subtract = new Priority(subtractPriority);

    public static final Priority RightBracket = new Priority(rightBracketPriority);

    public static final Priority Min = new Priority(minPriority);
    public static final Priority Max = new Priority(maxPriority);

    private static final Map<String, Priority> priorities = Map.of(
            "*", Multiply,
            "/", Divide,
            "+", Add,
            "-", Subtract,
            ")", RightBracket,
            "min", Min,
            "max", Max
    );

    public static Priority getExpressionPriority(String expressionMark) {
        return priorities.get(expressionMark);
    }

    private final int currentPriority;

    private Priority(int priority) {
        currentPriority = priority;
    }

    public Priority inc() {
        return new Priority(currentPriority + stepPriority);
    }

    public Priority dec() {
        return new Priority(currentPriority - stepPriority);
    }

    @Override
    public String toString() {
        return Integer.toString(currentPriority);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Priority second) {
            return currentPriority == second.currentPriority;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(currentPriority);
    }


}
