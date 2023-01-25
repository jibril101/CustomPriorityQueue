
import java.util.Random;

public class ProducerConsumer<T>  {

    private CustomPriorityQueue<T> queue;
    private Integer priority;
    private Random rand;
    private Item<T> item;

    public ProducerConsumer(CustomPriorityQueue<T> queue){
        this.queue = queue;
    }

    /**
     * @param burst
     * @return
     * @throws InterruptedException
     */
    public Item<T> produce() throws InterruptedException {
        Thread.sleep(1000);
       
        while (true) {
            while(queue.atMaxCapacity()) {
                
                System.out.print("\n-----QUEUE IS FULL-----\n");
                try {
                    //Blocking because queue is full, lock is released
                    queue.waitWhenFull();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    break;
                }
            }
                item = createRandomItem();
                System.out.print("\n");
                enqueue(item);
                Thread.sleep(1000);
                queue.notifyAllItemsExist();
        }

    }

    /**
     * @throws InterruptedException
     */
    public Item<T> consume() throws InterruptedException {
        
            Thread.sleep(1000);
            while(true) {
                
                while(queue.isEmpty()){
                    //Blocks consumer thread, and waits until notified by producer when it enqueues
                    try {
                        queue.waitWhenEmpty();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                Thread.sleep(2000);
                dequeue();

                //Unblocks Producer thread
                queue.notifyAllTheresSpaceInQueue();
            }
    }

    private void enqueue(Item<T> item) {
        try {
            queue.enqueue(item);
        } catch (Exception e) {
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