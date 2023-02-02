package expression;

public abstract class SuperExpressionImpl implements SuperExpression {

    public int getPriority1()
    {
     return getPriority();
    }

    public  boolean isRequired1()
    {
        return isRequired();
    }

    protected abstract int getPriority();
    protected abstract boolean isRequired();
}
