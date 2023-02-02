package expression;

import expression.handler.Handler;

import java.util.NoSuchElementException;

public class Variable<T> extends AbstractElement<T>{
    private final String variable;

    public Variable(String var) {
        this.variable = var;
    }

    @Override
    public T evaluate(T x, T y, T z, Handler<T> handler) {
        switch (variable) {
            case "x":
                return x;
            case "y":
                return y;
            case "z":
                return z;
        }
        throw new NoSuchElementException("Incorrect variable");
    }
}
