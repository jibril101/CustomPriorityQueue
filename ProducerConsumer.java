
import java.util.Random;

public class ProducerConsumer<T>  {

    private CustomPriorityQueue<T> queue;
    private Integer priority;
    private Random rand;
    private Item<T> item;

    public ProducerConsumer(CustomPriorityQueue<T> queue){
        this.queue = queue;
    }

    public Item<T> produce() throws InterruptedException {
        //Thread.sleep(1000);
       
        while (true) {

            synchronized(this) {
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
    }

    /**
     * @throws InterruptedException
     */
    public Item<T> consume() throws InterruptedException {
        
        synchronized(this) {
            Thread.sleep(2000);
            while(true) {
                
                while(queue.isEmpty()){
                    //Item<T> ret_val = new Item<>(-1, "Queue Empty!!!");
                    try {
                        System.out.print("----------QUEUE IS EMPTY---------WAITING FOR ITEMS---------\n");
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    queue.dequeue().getPriority();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                notifyAll();

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