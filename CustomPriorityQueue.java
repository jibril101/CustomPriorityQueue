import java.util.HashMap;
import java.util.TreeMap;
import java.util.concurrent.Semaphore;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.Map;
import java.util.NavigableSet;


public class CustomPriorityQueue<T> {

    private final Integer capacity; // this is the pre-determined queue size, set when creating the priorityQueue
    private Integer totalItems =0; // keep track of total items in queue
    private Integer throttleRate = 2; // Generalize so that each priority class can have an arbitrary throttle rate
    private TreeMap<Integer, Queue<Item<T>>> queues; // A Map of Queues based on Priority Classes
    private Map<Integer, Integer> counters; // Counting Dequeues for Priority classes for Throttle Rate
    private Integer dequeueState = 1;
    // private Semaphore mutex;
    // private Semaphore empty;
    private Semaphore queueCapacity;


    /** 
     * @param capacity
     */
    public CustomPriorityQueue(Integer capacity) {
        queues = new TreeMap<>();
        counters = new HashMap<>();
        this.capacity = capacity;
        this.queueCapacity = new Semaphore(0);
    }
    
    /**
     * @param myItem
     */
    public synchronized void enqueue(Item<T> myItem) throws Exception {
        /* perform enqueue. If the priority class does not exist then create it

           Add a throttle here aswell to prevent overflow of higher priority of items
           Note: If the rate of higher priority items entering the queue is higher than the rate of lower priority items
           the lower priority Items will take a long time to exit in theory. I don't it will NEVER exit since we have in 
           place a throttle for the dequeueing process.
        */ 
        /***
         * Performs an Enqueue into the custom priority queue
         */

        try{
            
            queueCapacity.release();
            // System.out.print("Semaphore " + queueCapacity.availablePermits() + " \n");
            int priority = myItem.getPriority();
            if (!queues.containsKey(priority)) {
                LinkedList<Item<T>> queue = new LinkedList<>();
                queue.add(myItem);
                queues.put(priority, queue);
                counters.put(priority,0);
            } else {
                queues.get(priority).add(myItem);
            }
            totalItems = totalItems + 1;

            System.out.print("Enqueueing " + priority + "\n");
            System.out.print("TotalItems " + totalItems + "\n");
            
        } catch(Exception exp) { // make a more speicfic exception
            // maybe decrement semaphore count here
            exp.printStackTrace();
        }

    }

    /**
     * @return
     * @throws InterruptedException
     */
    public synchronized Item<T> dequeue() throws InterruptedException{
        /* Assumptions: If X+1 priority class is empty then go to the
        next priority class and so on until you find a non-empty class
        if there are none then return 
        */

        queueCapacity.acquire();
        System.out.print("Capacity: " + queueCapacity.availablePermits() + "\n");
        System.out.print("In dequeue " + "\n");
        Item<T> ret_val = null;
        // set priority class to start with
        Set<Integer> priorityLevels = queues.keySet();
        try {
            if (!queues.containsKey(dequeueState)) {
                // the next priority doesnt exist get the next higher key after dequeueState - 1 
                NavigableSet<Integer> nonConstrainedQueues = queues.navigableKeySet().tailSet(dequeueState -1, false);  
                if (!nonConstrainedQueues.isEmpty()) {
                    priorityLevels = nonConstrainedQueues;
                }
            } else {
                priorityLevels = queues.navigableKeySet().tailSet(dequeueState, true);
            }
        } 
        catch (NullPointerException e) {
            System.out.print("Something went wrong when accessing priority levels");
        }

        
        for (Integer priority: priorityLevels) {
            boolean cond = !queues.get(priority).isEmpty();
            System.out.print(queues.get(priority) + " \n");
            if (cond) { //&& counters.get(priority) < throttleRate)
                Item<T> item = queues.get(priority).poll();
                counters.compute(priority,(k,v) -> (v + 1));
                System.out.print("Dequeueing " + priority + "\n");
                ret_val = item;
                if (counters.get(priority) == throttleRate) { 
                    // set dequeue state for next dequeue state to priority class x + 1 and set counter to 0
                    dequeueState = priority + 1;
                    counters.replace(priority,0);
                    // check if this priority class exist
                } else {
                    dequeueState = 1;
                }
                totalItems = totalItems - 1;
                System.out.print("totalItems avail " + queueCapacity.availablePermits() + "\n");
                return ret_val;
            } else {
                // move to the next priority class
                continue;
            }
        }
        if (ret_val == null) {
            // case, x + 1 or any other lower priority not avail. Throw exception
            System.out.println("No X + 1 or lower priority available in the Queue");
        }
        System.out.print( "Totalitems: " + totalItems + "\n");
        return ret_val;
    }

    public synchronized boolean atMaxCapacity(){
        // Check is the queue has reach maximum capacity
        return totalItems > capacity;
    }

}
