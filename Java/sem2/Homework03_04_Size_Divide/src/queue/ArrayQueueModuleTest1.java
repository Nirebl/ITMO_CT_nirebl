package queue;

public class ArrayQueueModuleTest1 {

    public static void main(String[] args) {

        for (int i = 0; i < 4; ++i) {
            ArrayQueueModule.enqueue(i);
        }

        System.out.println("Queue size: " + ArrayQueueModule.size());


    }

}
