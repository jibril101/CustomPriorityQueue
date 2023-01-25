
import java.util.Random;
import java.io.PrintWriter;
import java.io.IOException;

public class ProducerConsumer<T>  implements Runnable{

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
                
                System.out.print("\n*QUEUE FULL*\n");
                try {
                    //Blocking because queue is full, lock is released
                    queue.waitWhenFull();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    break;
                }
            }
                item = createRandomItem();
                enqueue(item);
                Thread.sleep(4000);
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
                        System.out.print("\n*QUEUE EMPTY*\n");
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
        } catch (EnqueueException e) {
            e.printStackTrace();
            System.out.print("Something went wrong while enqueing, check if queue has space");
        }
    }

    private void dequeue() {
        try {
            queue.dequeue().getPriority();
        } catch (DequeueException e) {
            System.out.print("Something went wrong while dequeuing,check if priority levels");
            e.printStackTrace();
            
        }
    }

    public Integer generateRandom(){
        try{
            rand = new Random();
            priority = 1 + rand.nextInt(10);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return priority;
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

    public void writeStream(){
        // Maybe Output to file first and then make enque read priority from the file
        try {
            try (PrintWriter writer = new PrintWriter("input.txt")) {
                writer.println(generateRandom());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally{

        }
    }

    // @Override
    // public void run() {
    //     try {
    //         consume();
    //     } catch (InterruptedException e) {
    //         // TODO Auto-generated catch block
    //         e.printStackTrace();
    //     }
        
    // }
}