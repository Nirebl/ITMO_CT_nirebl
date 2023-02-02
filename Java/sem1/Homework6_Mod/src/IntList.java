import java.util.Arrays;

public class IntList {
    private int[] array;
    private int size;
    private int cntElements;

    IntList(int n) {
        array = new int[n];
        size = n;
        cntElements = n;
    }

    void resize(int newSize) {
        array = Arrays.copyOf(array, newSize);
        size = newSize;
    }

    void pushBack(int n) {
        if (cntElements + 1 >= size) {
            resize(size * 2);
        }
        array[cntElements] = n;
        cntElements++;
    }

    void plus(int index, int value) {
        array[index] += value;
    }

    void set(int index, int value) {
        array[index] = value;
    }

    int get(int index) {
        return array[index];
    }

    int size() {
        return cntElements;
    }
}
