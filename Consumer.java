


public class Consumer<T> {
    private CustomPriorityQueue<T> queue;
    
    public Consumer(CustomPriorityQueue<T> queue){
        this.queue = queue;
    }

    /**
     * @throws InterruptedException
     */
    public Item<T> consume() throws InterruptedException {

        while(true) {
            
            synchronized (this) {
                
                if(queue.isEmpty()){
                    Item<T> ret_val = new Item<>(-1, "Queue Empty!!!");
                    wait();
                }
                try {
                    queue.dequeue().getPriority();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                notify();

            }


        }
    }
}