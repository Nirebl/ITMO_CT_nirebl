package queue;

//import org.junit.jupiter.api.Test;

import java.util.Random;

//import static org.junit.jupiter.api.Assertions.*;

class ArrayQueueADTTest {
    //@Test
    public static void main(String[] args) {
        Random rand = new Random();

        int repeats = 10;
        while (repeats-- > 0) {
            ArrayQueueADT a = new ArrayQueueADT();
            int i = rand.nextInt(100) + 1;
            while (i-- > 0) {
                int number = rand.nextInt(50);
                ArrayQueueADT.push(a, number);
            }
            ArrayQueueADT.clear(a);
            System.out.println("Actual:" + ArrayQueueADT.size(a));
            System.out.println("Expected: 0");
            //assertEquals(0, ArrayQueueADT.size(a));
        }
        repeats = 10;
        while (repeats-- > 0) {
            ArrayQueueADT a = new ArrayQueueADT();
            int i = rand.nextInt(100) + 1;
            int size = i;
            while (i-- > 0) {
                int number = rand.nextInt(50);
                ArrayQueueADT.push(a, number);
            }
            System.out.println("Actual: " + ArrayQueueADT.size(a));
            System.out.println("Expected: " + size);
            //assertEquals(size, ArrayQueueADT.size(a));
        }

        repeats = 10;
        while (repeats-- > 0) {
            ArrayQueueADT a = new ArrayQueueADT();
            int lastElement = -1;
            int i = rand.nextInt(100) + 1;
            while (i-- > 0) {
                int number = rand.nextInt(50);
                ArrayQueueADT.push(a, number);
                lastElement = number;
            }
            System.out.println("Actual:" + ArrayQueueADT.element(a));
            System.out.println("Expected: " + lastElement);
            //assertEquals(lastElement, ArrayQueueADT.element(a));
        }
        repeats = 10;
        while (repeats-- > 0) {
            ArrayQueueADT a = new ArrayQueueADT();
            int i = rand.nextInt(100) + 1;
            int size = i;
            while (i-- > 0) {
                int number = rand.nextInt(50);
                ArrayQueueADT.push(a, number);
            }
            ArrayQueueADT.remove(a);
            //assertEquals(size - 1, ArrayQueueADT.size(a));
            System.out.println("Actual:" + ArrayQueueADT.size(a));
            System.out.println("Expected: " + (size - 1));
        }

        repeats = 10;
        while (repeats-- > 0) {
            ArrayQueueADT a = new ArrayQueueADT();
            int i = rand.nextInt(100) + 1;
            int findNumber = rand.nextInt(100);
            int cnt = 0;
            while (i-- > 0) {
                int number = rand.nextInt(100);
                if (number == findNumber) {
                    cnt++;
                }
                ArrayQueueADT.push(a, number);
            }
            //assertEquals(cnt, ArrayQueueADT.count(a, findNumber));
            System.out.println("Actual:" + ArrayQueueADT.count(a, findNumber));
            System.out.println("Expected: " + cnt);
        }
    }
}