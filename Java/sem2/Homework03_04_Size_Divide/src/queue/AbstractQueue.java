package queue;

import java.lang.reflect.Constructor;
import java.util.function.Function;
import java.util.function.Predicate;

public abstract class AbstractQueue implements Queue {
    private int size = 0;

    protected int IncreaseSize() {
        return ++size;
    }

    protected int DecreaseSize() {
        return --size;
    }

    protected void ResetSize() {
        size = 0;
    }
    //Let eq(n) is:
    //(i in [1..n] -> a[i]' = a[i])

    //Let immutable(q):
    //n'=n && eq(n)



    /// Pred: true
    // Post: R = n && immutable
    @Override
    public int size() {
        return size;
    }

    // Pred: true
    // Post: R = (n == 0) && immutable(container)
    @Override
    public boolean isEmpty() {
        return size == 0;
    }


    protected abstract Queue getInstance();


    @Override
    public Queue map(Function<Object, Object> function) {
        var result = getInstance();
        for (int i = 0; i < size; i++) {
            Object element = this.dequeue();
            result.enqueue(function.apply(element));
            this.enqueue(element);
        }
        return result;
    }


    @Override
    public Queue filter(Predicate<Object> pred) {
        var result = getInstance();
        for (int i = 0; i < size; i++) {
            Object element = this.dequeue();

            if (pred.test(element))
                result.enqueue(element);

            this.enqueue(element);
        }
        return result;
    }

}
