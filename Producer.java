
import java.util.Random;

public class Producer<T>  {

    private CustomPriorityQueue<T> queue;
    private Integer priority;
    private Random rand;
    private Integer upperbound;
    private Item<T> item;

    public Producer(CustomPriorityQueue<T> queue){
        this.queue = queue;
        this.rand = new Random();
    }

    public synchronized void produce() throws InterruptedException {
       
        while (true) {
            
            while(queue.atMaxCapacity()) {
               System.out.println("\nWaiting for Queue to Dequeue Items\n");
               wait();
            }

            item = createItem();
            try {
                Thread.sleep(2500);
                queue.enqueue(item);
            } catch (Exception e) {
                e.printStackTrace();
            }
            notify();
        }
    }



    /* (non-Javadoc)
     * @see java.lang.Runnable#run()
     */
    // @Override
    // public void run() {
    //    try {
    //     item = createItem();
    //     Thread.sleep(10000);
    //     queue.enqueue(item);

    //    } catch (Exception exp) {
    //     System.out.print(exp);
    //    } 
    // }

    public Item<T> createItem(){
        priority = 1 + rand.nextInt(upperbound + 1);
        item = new Item<>(priority, "Priority: " + priority + "\n");
        return item;

    }
}
