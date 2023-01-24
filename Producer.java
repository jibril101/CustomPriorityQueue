
import java.util.Random;

public class Producer<T>  {

    private CustomPriorityQueue<T> queue;
    private Integer priority;
    private Random rand;
    private Item<T> item;

    public Producer(CustomPriorityQueue<T> queue){
        this.queue = queue;
    }

    public synchronized Item<T> produce() throws InterruptedException {
       
        while (true) {
            
            synchronized(this) {

                if(queue.atMaxCapacity()) {
                    System.out.print("reached cap");
                    item = new Item<>(-1, "QUEUE FULL");
                    wait();
                }
            
                item = createItem();
                try {
                    queue.enqueue(item);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                notify();
            }

        }
    }

    public Item<T> createItem() {
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