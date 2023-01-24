


public class Consumer<T> {
    private CustomPriorityQueue<T> queue;
    
    public Consumer(CustomPriorityQueue<T> queue){
        this.queue = queue;
    }

    /**
     * @throws InterruptedException
     */
    public synchronized Item<T> consume() throws InterruptedException {
        Thread.sleep(1000);
        while(true) {

                while(queue.isEmpty()){
                    //Item<T> ret_val = new Item<>(-1, "Queue Empty!!!");
                    try {
                        System.out.print("------------QUEUE IS EMPTY----------------\n");
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
                notify();

        }
    }
}