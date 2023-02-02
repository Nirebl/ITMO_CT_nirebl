import java.util.Arrays;

public class IntListOld {
    private int[] array = new int[0];
    private int size = 0;

    IntListOld(int n) {
        array = new int[n];
        size = n;
    }

    void resize(int newSize) {
        array = Arrays.copyOf(array, newSize);
        size = newSize;
    }

    void pushBack(int n) {
        resize(size + 1);
        array[size - 1] = n;
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
        return size;
    }
}
