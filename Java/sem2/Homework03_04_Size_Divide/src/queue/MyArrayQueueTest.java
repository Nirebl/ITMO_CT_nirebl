package queue;

//import org.junit.jupiter.api.Test;

import java.util.Random;

//import static org.junit.jupiter.api.Assertions.*;

class MyArrayQueueTest {
    //@Test
    public static void main(String[] args) {
        Random rand = new Random();

        int repeats = 10;
        while (repeats-- > 0) {
            ArrayQueue a = new ArrayQueue();
            int i = rand.nextInt(100) + 1;
            while (i-- > 0) {
                int number = rand.nextInt(50);
                a.push(number);
            }
            a.clear();
            System.out.println("Actual: " + a.size());
            System.out.println("Expected: 0");
        }

        repeats = 10;
        while (repeats-- > 0) {
            ArrayQueue a = new ArrayQueue();
            int i = rand.nextInt(100) + 1;
            int size = i;
            while (i-- > 0) {
                int number = rand.nextInt(50);
                a.push(number);
            }
            System.out.println("Actual: " + a.size());
            System.out.println("Expected: " + size);
        }

        repeats = 10;
        while (repeats-- > 0) {
            ArrayQueue a = new ArrayQueue();
            int lastElement = -1;
            int i = rand.nextInt(100) + 1;
            while (i-- > 0) {
                int number = rand.nextInt(50);
                a.push(number);
                lastElement = number;
            }
            System.out.println("Actual: " + a.element());
            System.out.println("Expected: " + lastElement);
        }
        repeats = 10;
        while (repeats-- > 0) {
            ArrayQueue a = new ArrayQueue();
            int i = rand.nextInt(100) + 1;
            int size = i;
            while (i-- > 0) {
                int number = rand.nextInt(50);
                a.push(number);
            }
            a.remove();
            System.out.println("Actual: " + a.size());
            System.out.println("Expected: " + (size - 1));
        }

        repeats = 10;
        while (repeats-- > 0) {
            ArrayQueue a = new ArrayQueue();
            int i = rand.nextInt(100) + 1;
            int findNumber = rand.nextInt(100);
            int cnt = 0;
            while (i-- > 0) {
                int number = rand.nextInt(100);
                if (number == findNumber) {
                    cnt++;
                }
                a.push(number);
            }
            System.out.println("Actual: " + a.count(findNumber));
            System.out.println("Expected: " + cnt);
        }
    }
}