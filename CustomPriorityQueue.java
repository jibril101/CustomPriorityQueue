import java.util.HashMap;
import java.util.TreeMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.Map;
import java.util.NavigableSet;


public class CustomPriorityQueue<T> {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    private final Integer capacity; // this is the pre-determined queue size, set when creating the priorityQueue
    private Integer totalItems = 0; // keep track of total items in queue
    private Integer throttleRate = 2; // Generalize so that each priority class can have an arbitrary throttle rate
    private TreeMap<Integer, Queue<Item<T>>> queues; // A Map of Queues based on Priority Classes
    private Map<Integer, Integer> counters; // Counting Dequeues for Priority classes for Throttle Rate
    private Integer dequeueState = 1;
    private final Object FULL_QUEUE = new Object();
    private final Object EMPTY_QUEUE = new Object();

    /** 
     * @param capacity
     */
    public CustomPriorityQueue(Integer capacity) {
        queues = new TreeMap<>();
        counters = new HashMap<>();
        this.capacity = capacity;
    }

    /**
     * @throws InterruptedException
     */
    public void waitWhenFull() throws InterruptedException {
        synchronized(FULL_QUEUE) {
            FULL_QUEUE.wait();
        }
    }

    public void notifyAllTheresSpaceInQueue(){
        // Consumer calls this to wake up Producer threads, 
        // If Consumer Acquires the FULL_QUEUE lock first, it will just descope and release
        synchronized(FULL_QUEUE){
            FULL_QUEUE.notifyAll();
        }
    }

    /**
     * @throws InterruptedException
     */
    public void waitWhenEmpty() throws InterruptedException {
        synchronized(EMPTY_QUEUE){
            EMPTY_QUEUE.wait();
        }
    }

    public void notifyAllItemsExist() {
        synchronized(EMPTY_QUEUE) {
            EMPTY_QUEUE.notifyAll();
        }
    }
    
    /**
     * @param myItem
     */
    public synchronized void enqueue(Item<T> myItem) throws EnqueueException {
        /***
         * Performs an Enqueue into the custom priority queue
         * If the priority class does not exist then create it
         * Add a throttle here aswell to prevent overflow of higher priority of items 
         * Note: If the rate of higher priority items entering the queue is higher than the rate of lower priority items
         * the lower priority Items will take a long time to exit in theory. I don't think it will NEVER exit since we have in 
         * place a throttle for the dequeueing process but it wont be practical.
         */


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
        System.out.print(priority + "  ");
        //System.out.print(Thread.currentThread().getName() + " ");

    }

    /**
     * @return
     * @throws Exception
     */
    public synchronized Item<T> dequeue() throws DequeueException {
        /* Assumptions: If X+1 priority class is empty then go to the
        next priority class and so on until you find a non-empty class
        if you exhaust the priorities, reset dequeue state and return  
        */

        Item<T> ret_val = null;
        // if (totalItems == 0) {
        //     ret_val = new Item<>(-1, "Queue Empty!!!");
        //     return ret_val;
        // }

        // set priority class to start with from dequeueState
        Set<Integer> priorityLevels = queues.keySet();
      
        if (!queues.containsKey(dequeueState)) {
            // the next priority doesnt exist get the next higher key after dequeueState - 1 
            NavigableSet<Integer> nonConstrainedQueues = queues.navigableKeySet().tailSet(dequeueState -1, false);  
            if (!nonConstrainedQueues.isEmpty()) {
                priorityLevels = nonConstrainedQueues;
            }
        } else {
            priorityLevels = queues.navigableKeySet().tailSet(dequeueState, true);
        }

        
        for (Integer priority: priorityLevels) {
            boolean cond = !queues.get(priority).isEmpty();
            if (cond) { //&& counters.get(priority) < throttleRate)
                Item<T> item = queues.get(priority).poll();
                counters.compute(priority,(k,v) -> (v + 1));
                // System.out.print("Dequeueing " + priority + "\n");
                System.out.print(ANSI_YELLOW + priority + "  " + ANSI_RESET );
                ret_val = item;
                //System.out.print("Current Count for: " + priority + " is " + counters.get(priority) + "\n");
                if (counters.get(priority) == throttleRate) { 
                    // set dequeue state for next dequeue state to priority class x + 1 and set counter to 0
                    dequeueState = priority + 1;
                    counters.replace(priority,0);
                    //System.out.println("Throttle Reached for :" + priority + "\n");
                    // check if this priority class exist
                } else {
                    dequeueState = 1;
                }
                totalItems = totalItems - 1;
                return ret_val;
            } else {
                // move to the next priority class
                continue;
            }
        }
        if (ret_val == null) {
            // case, x + 1 or any other lower priority not avail. Reset dequeState 
            if (totalItems > 0) {
                dequeueState = 1;
            }
            ret_val = new Item<>(-1, "\nNo X + 1 Or Lower Priority Available In The Queue !!!\n");
            System.out.print("\n**Nothing Left to DEQUEUE, Go Check Higher Priority Classes *\n");
        }
        return ret_val;
    }

    public synchronized boolean atMaxCapacity(){
        // Check is the queue has reach maximum capacity
        return totalItems > capacity;
    }
    public synchronized boolean isEmpty(){
        return totalItems == 0;
    }
    public synchronized TreeMap<Integer, Queue<Item<T>>> returnQueue(){
        return queues;
    }

    public void setTotalItems(Integer totalItems) {
        this.totalItems = totalItems;
    }

    public void setThrottleRate(Integer throttleRate) {
        this.throttleRate = throttleRate;
    }

    public void setQueues(TreeMap<Integer, Queue<Item<T>>> queues) {
        this.queues = queues;
    }

    public void setCounters(Map<Integer, Integer> counters) {
        this.counters = counters;
    }

    public void setDequeueState(Integer dequeueState) {
        this.dequeueState = dequeueState;
    }
}
