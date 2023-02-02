import java.util.Arrays;

public class IntList2 {
    private int[] array = new int[0];
    private int size = 0;
    private int cntElements = 1;

    IntList2(int n) {
        array = new int[n];
        size = n;
    }

    void resize(int newSize) {
        array = Arrays.copyOf(array, newSize);
        size = newSize;
    }

    void pushBack(int n) {
        while (cntElements + 1 >= size) {
            resize(size * 2);
        }
        array[cntElements] = n;
        cntElements++;
    }

    void add(int index, int value) {
        array[index] += value;
    }

    int get(int index) {
        return array[index];
    }

    int size() {
        return cntElements;
    }
}
