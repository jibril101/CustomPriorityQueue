
import java.util.Random;

public class ProducerConsumer<T>  {

    private CustomPriorityQueue<T> queue;
    private Integer priority;
    private Random rand;
    private Item<T> item;

    public ProducerConsumer(CustomPriorityQueue<T> queue){
        this.queue = queue;
    }

    public Item<T> produce(boolean burst) throws InterruptedException {
        Thread.sleep(500);
       
        while (true) {

            synchronized(this) {
                while(queue.atMaxCapacity()) {
                    System.out.print("-------------QUEUE IS FULL-------WAITING FOR DEQUEU--------\n");
                    item = new Item<>(-1, "QUEUE FULL");
                    waitForItems();
                }
                item = createRandomItem();
                if (burst) {
                    enqueue();
                    Thread.sleep(500);
                } else {
                    System.out.print("\n");
                    enqueue();
                    System.out.print("Single Enqueue\n");
                    Thread.sleep(500);
                    waitForItems();

                }
                //System.out.print("This should wake up the Dequeue\n");
                
                notifyAll();
            }
        }
    }

    private void enqueue() {
        try {
            queue.enqueue(item);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @throws InterruptedException
     */
    public Item<T> consume(boolean burst) throws InterruptedException {
        
        synchronized(this) {
            Thread.sleep(100);
            while(true) {
                
                while(queue.isEmpty()){
                    waitForItems();
                }
                if (burst) {
                    Thread.sleep(1000);
                    dequeue();
                } else {
                    System.out.print("\n");
                    dequeue();
                    System.out.print("Single Dequeue\n");
                    waitForItems();
                }
                
                notifyAll();

            }
        }
       
    }

    private void waitForItems() {
        try {
            System.out.print("----------QUEUE IS EMPTY---------WAITING FOR ITEMS---------\n");
            wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void dequeue() {
        try {
            queue.dequeue().getPriority();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Item<T> createRandomItem() {
        try{
            rand = new Random();
            priority = 1 + rand.nextInt(10);
        } catch(Exception e) {
            e.printStackTrace();
        }
        item = new Item<>(priority, "Priority");
        return item;
    }
}