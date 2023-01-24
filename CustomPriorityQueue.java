import java.util.HashMap;
import java.util.TreeMap;
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


    /** 
     * @param capacity
     */
    public CustomPriorityQueue(Integer capacity) {
        queues = new TreeMap<>();
        counters = new HashMap<>();
        this.capacity = capacity;
    }
    
    /**
     * @param myItem
     */
    public void enqueue(Item<T> myItem) throws Exception {
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
            
        } catch(Exception exp) { // make a more speicfic exception
            // maybe decrement semaphore count here
            exp.printStackTrace();
        }

    }

    /**
     * @return
     * @throws Exception
     */
    public Item<T> dequeue() throws Exception {
        /* Assumptions: If X+1 priority class is empty then go to the
        next priority class and so on until you find a non-empty class
        if there are none then return 
        */

        Item<T> ret_val = null;
        if (totalItems == 0) {
            ret_val = new Item<>(-1, "Queue Empty!!!");
            return ret_val;
        }

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
                return ret_val;
            } else {
                // move to the next priority class
                continue;
            }
        }
        if (ret_val == null) {
            // case, x + 1 or any other lower priority not avail. Throw exception
            ret_val = new Item<>(-1, "\nNo X + 1 Or Lower Priority Available In The Queue !!!\n");
        }
        System.out.print( "Totalitems: " + totalItems + "\n");
        return ret_val;
    }

    public boolean atMaxCapacity(){
        // Check is the queue has reach maximum capacity
        return totalItems > capacity;
    }
    public boolean isEmpty(){
        return totalItems == 0;
    }
    public TreeMap<Integer, Queue<Item<T>>> returnQueue(){
        return queues;
    }
}
