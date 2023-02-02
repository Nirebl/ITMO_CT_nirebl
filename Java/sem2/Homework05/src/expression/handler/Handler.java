package expression.handler;

public interface Handler<T> {
    T cast(int value);
    T parse(String value);

    T add(T x, T y);
    T subtract(T x, T y);
    T multiply(T x, T y);
    T divide(T x, T y);

    T max(T x, T y);
    T min(T x, T y);

    T negate(T x);

    T count(T x);
}
