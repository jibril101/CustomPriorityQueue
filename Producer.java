
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

                while(queue.atMaxCapacity()) {
                    System.out.print("reached capicity\n");
                    item = new Item<>(-1, "QUEUE FULL");
                    wait();
                }
                
                item = createItem();
                try {
                    queue.enqueue(item);
                    System.out.print(queue.returnQueue().get(item.getPriority()) + "\n");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.print("This should wake up the Dequeue\n");
                notifyAll();

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