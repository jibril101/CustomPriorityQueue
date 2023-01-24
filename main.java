
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;


public class main {
    public static <T> void main(String[] args) {
        int numThreads = 2;

        
        CustomPriorityQueue<T> queue = new CustomPriorityQueue<>(4);
        // Producer<T> producer = new Producer<>(queue);
        // Consumer<T> consumer = new Consumer<>(queue);
        // Thread thread1 = new Thread(producer);
        // thread1.start();
        // Thread thread2 = new Thread(consumer);
        // thread2.start();


        Producer<T> producer = new Producer(queue);
        Consumer<T> consumer = new Consumer(queue);
        Thread threadP = new Thread(producer, "producer");
        Thread threadC = new Thread(consumer, "consumer");

        threadP.start();
        
        threadC.start();
        
        // try {
        //     threadP.join();
        //     threadC.join();
        // } catch (InterruptedException e) {
        //     e.printStackTrace();
        // }
      

    //     Item<T> a = new Item<>(1);
    //     Item<T> b = new Item<>(1);
    //     Item<T> c = new Item<>(1);
    //     Item<T> d = new Item<>(2);
    //     Item<T> e = new Item<>(1);
    //     Item<T> f = new Item<>(10);
        
    //    try {
    //     queue.enqueue(a);
    //     queue.enqueue(b);
    //     queue.enqueue(c);
    //     queue.enqueue(d);
    //     queue.enqueue(e);
    //     queue.enqueue(f);

        
    //    } catch (Exception exp) {
    //     System.out.print(exp);
    //    }

    //     System.out.print(queue.dequeue().getPriority() + "\n");
    //     System.out.print(queue.dequeue().getPriority() + "\n");
    //     System.out.print(queue.dequeue().getPriority() + "\n");
    //     System.out.print(queue.dequeue().getPriority() + "\n");
    //     System.out.print(queue.dequeue().getPriority() + "\n");
    //     System.out.print(queue.dequeue().getPriority() + "\n");
    }
}