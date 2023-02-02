package queue;

import java.util.function.Function;
import java.util.function.Predicate;

public interface Queue {
    //Let eq(n) is:
    //(i in [1..n] -> a[i]' = a[i])

    //Let immutable(q):
    //n'=n && eq(n)

    // Pre: element != null
    // Post: n' = n + 1 && eq(n) && a[n + 1]' = element
    void enqueue(Object element);
    // Pre: queue.n > 0
    // Post: R = queue.a[1] && immutable(container)
    Object element();
    // Pre: queue.n > 0
    // Post: queue.n' = queue.n - 1 && (i in [1..n-1] -> queue.a[i]' = queue.a[i + 1])
    // R = queue.a[1]
    Object dequeue();
    // Pre: true
    // Post: R = queue.n && immutable(queue)
    int size();
    // Pred: true
    // Post: R = (n == 0) && immutable(container)
    boolean isEmpty();
    // Pred: true
    // Post: n = 0
    void clear();

    //Pre: true
    //Post: immutable(container) && (foreach i=0..n' : result = queue | queue.container[i] = function(queue.container[i]))
    Queue map(Function<Object, Object> function);
    //Pre: true
    //Post: immutable(container) && (foreach i=0..n' : result = queue | predicate(queue.container[i]) = true)
    Queue filter(Predicate<Object> pred);

}
